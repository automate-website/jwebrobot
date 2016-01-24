package website.automate.jwebrobot.executor;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import website.automate.jwebrobot.AbstractTest;
import website.automate.jwebrobot.context.GlobalExecutionContext;
import website.automate.jwebrobot.loader.ScenarioFile;
import website.automate.jwebrobot.models.factories.ScenarioFactory;
import website.automate.jwebrobot.models.scenario.Scenario;
import website.automate.jwebrobot.models.scenario.actions.Action;
import website.automate.jwebrobot.models.scenario.actions.ClickAction;
import website.automate.jwebrobot.models.scenario.actions.EnsureAction;
import website.automate.jwebrobot.models.scenario.actions.OpenAction;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static java.util.Arrays.asList;

@RunWith(MockitoJUnitRunner.class)
public class ScenarioExecutorTest extends AbstractTest {

    private ScenarioExecutor scenarioExecutor;

    private OpenAction openAction;
    private ClickAction clickAction;
    private EnsureAction ensureAction;
    private ScenarioFactory scenarioFactory;
    
    @Mock private File file;

    @Before
    public void setUp() {
        scenarioFactory = injector.getInstance(ScenarioFactory.class);
        scenarioExecutor = injector.getInstance(ScenarioExecutor.class);

        openAction = new OpenAction();
        openAction.setUrl("https://en.wikipedia.org");

        clickAction = new ClickAction();
        clickAction.setSelector("a[title=\"Wikipedia:About\"]");

        ensureAction = new EnsureAction();
        ensureAction.setSelector("#About_Wikipedia");
    }

    @Test
    public void simpleActionsShouldBeExecuted() {
        Scenario scenario = new Scenario();
        scenario.setSteps(Arrays.<Action>asList(openAction, clickAction, ensureAction));

        scenarioExecutor.execute(asContext(scenario));
    }
    
    private GlobalExecutionContext asContext(Scenario ... scenarios){
        return new GlobalExecutionContext(asScenarioFiles(scenarios), new ExecutorOptions());
    }
    
    private List<ScenarioFile> asScenarioFiles(Scenario ... scenarios){
        return asList(new ScenarioFile(asList(scenarios), file));
    }
}
