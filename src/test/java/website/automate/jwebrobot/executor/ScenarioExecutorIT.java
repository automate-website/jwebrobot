package website.automate.jwebrobot.executor;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import website.automate.jwebrobot.AbstractTest;
import website.automate.jwebrobot.context.GlobalExecutionContext;
import website.automate.jwebrobot.exceptions.RecursiveScenarioInclusionException;
import website.automate.jwebrobot.loader.ScenarioFile;
import website.automate.jwebrobot.loader.ScenarioLoader;
import website.automate.jwebrobot.model.Action;
import website.automate.jwebrobot.model.ActionType;
import website.automate.jwebrobot.model.Scenario;

import java.io.File;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static java.lang.ClassLoader.getSystemResourceAsStream;
import static java.util.Arrays.asList;

@RunWith(MockitoJUnitRunner.class)
public class ScenarioExecutorIT extends AbstractTest {

    private ScenarioExecutor scenarioExecutor;

    private Action openAction;
    private Action clickAction;
    private Action ensureAction;

    @Mock private File file;

    @Before
    public void setUp() {
        scenarioExecutor = injector.getInstance(ScenarioExecutor.class);

        openAction = new Action();
        openAction.setType(ActionType.OPEN);
        openAction.setUrl("https://en.wikipedia.org");

        clickAction = new Action();
        clickAction.setType(ActionType.CLICK);
        clickAction.setSelector("a[title=\"Wikipedia:About\"]");

        ensureAction = new Action();
        ensureAction.setType(ActionType.ENSURE);
        ensureAction.setSelector("#About_Wikipedia");
    }

    @Test
    public void simpleActionsShouldBeExecuted() {
        Scenario scenario = new Scenario();
        scenario.setSteps(Arrays.<Action>asList(openAction, clickAction, ensureAction));

        scenarioExecutor.execute(asContext(scenario));
    }

    @Test(expected = RecursiveScenarioInclusionException.class)
    public void shouldDetectCircularDependency() {
        ScenarioLoader scenarioLoader = injector.getInstance(ScenarioLoader.class);
        InputStream stream = getSystemResourceAsStream("./failing_scenarios/circular-dependency.yaml");
        List<Scenario> scenarios = scenarioLoader.createFromInputStream(stream);

        scenarioExecutor.execute(asContext(scenarios.toArray(new Scenario[scenarios.size()])));
    }

    private GlobalExecutionContext asContext(Scenario ... scenarios){
        return new GlobalExecutionContext(asScenarioFiles(scenarios), new ExecutorOptions(), new HashMap<String, Object>());
    }

    private List<ScenarioFile> asScenarioFiles(Scenario ... scenarios){
        return asList(new ScenarioFile(asList(scenarios), file));
    }
}
