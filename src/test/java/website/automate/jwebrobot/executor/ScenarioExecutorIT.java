package website.automate.jwebrobot.executor;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Before;
import org.junit.Rule;
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

    public static final int MOCK_SERVER_PORT = 8089;
    public static final String PACKAGE = "./src/test/resources/website/automate/jwebrobot";

    private ScenarioExecutor scenarioExecutor;
    private ScenarioLoader scenarioLoader;

    private Action openAction;
    private Action clickAction;
    private Action ensureAction;

    @Mock private File file;

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(MOCK_SERVER_PORT);

    @Before
    public void setUp() {
        scenarioLoader = injector.getInstance(ScenarioLoader.class);

        scenarioExecutor = injector.getInstance(ScenarioExecutor.class);

        openAction = new Action();
        openAction.setType(ActionType.OPEN);
        openAction.setUrl("http://localhost:" + MOCK_SERVER_PORT);

        clickAction = new Action();
        clickAction.setType(ActionType.CLICK);
        clickAction.setSelector("a[title=\"About\"]");

        ensureAction = new Action();
        ensureAction.setType(ActionType.ENSURE);
        ensureAction.setSelector("#About");

    }

    @Test
    public void simpleActionsShouldBeExecuted() {
        Scenario scenario = new Scenario();
        scenario.setSteps(Arrays.<Action>asList(openAction, clickAction, ensureAction));

        scenarioExecutor.execute(asContext(Arrays.asList(scenario)));
    }

    @Test
    public void clickByDefaultSelectorShouldWork() {
        List<Scenario> scenarios = getScenarios(PACKAGE + "/executor/click-by-selector-should-work.yaml");

        scenarioExecutor.execute(asContext(scenarios));
    }

    @Test
    public void clickByTextShouldWork() {
        List<Scenario> scenarios = getScenarios(PACKAGE + "/executor/click-by-text-should-work.yaml");

        scenarioExecutor.execute(asContext(scenarios));
    }
    
    @Test
    public void clickByParentSelectorShouldWork() {
        List<Scenario> scenarios = getScenarios(PACKAGE + "/executor/click-by-parent-selector-should-work.yaml");

        scenarioExecutor.execute(asContext(scenarios));
    }
    
    @Test
    public void clickBySelectorAndTextShouldWork() {
        List<Scenario> scenarios = getScenarios(PACKAGE + "/executor/click-by-partial-text-and-selector-should-work.yaml");

        scenarioExecutor.execute(asContext(scenarios));
    }

    @Test(expected = RecursiveScenarioInclusionException.class)
    public void shouldDetectCircularDependency() {
        InputStream stream = getSystemResourceAsStream("./failing_scenarios/circular-dependency.yaml");
        List<Scenario> scenarios = scenarioLoader.createFromInputStream(stream);

        scenarioExecutor.execute(asContext(scenarios));
    }

    private GlobalExecutionContext asContext(List<Scenario> scenarios){
        return new GlobalExecutionContext(asScenarioFiles(scenarios), new ExecutorOptions(), new HashMap<String, Object>());
    }

    private List<ScenarioFile> asScenarioFiles(List<Scenario> scenarios){
        return asList(new ScenarioFile(scenarios, file));
    }

    private List<Scenario> getScenarios(String scenarioPath) {
        return scenarioLoader
            .load(scenarioPath)
            .get(0)
            .getScenarios();
    }
}
