package website.automate.jwebrobot.executor;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import website.automate.jwebrobot.AbstractTest;
import website.automate.jwebrobot.ConfigurationProperties;
import website.automate.jwebrobot.context.GlobalExecutionContext;
import website.automate.jwebrobot.exceptions.RecursiveScenarioInclusionException;
import website.automate.jwebrobot.loader.ScenarioFile;
import website.automate.jwebrobot.loader.ScenarioLoader;
import website.automate.waml.io.model.Scenario;
import website.automate.waml.io.model.action.Action;
import website.automate.waml.io.model.action.ClickAction;
import website.automate.waml.io.model.action.EnsureAction;
import website.automate.waml.io.model.action.OpenAction;
import website.automate.waml.io.model.criteria.FilterCriteria;
import website.automate.waml.io.model.criteria.OpenCriteria;
import java.io.InputStream;
import java.util.*;

import static java.lang.ClassLoader.getSystemResourceAsStream;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonMap;

public class ScenarioExecutorIT extends AbstractTest {

    public static final int MOCK_SERVER_PORT = 8089;
    public static final String PACKAGE = "./src/test/resources/website/automate/jwebrobot";

    @Autowired
    private ScenarioExecutor scenarioExecutor;
    @Autowired
    private ScenarioLoader scenarioLoader;

    private OpenAction openAction;
    private ClickAction clickAction;
    private EnsureAction ensureAction;

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(MOCK_SERVER_PORT);

    @Before
    public void setUp() {
        openAction = new OpenAction();
        OpenCriteria openCriteria = new OpenCriteria();
        openCriteria.setUrl("http://localhost:" + MOCK_SERVER_PORT);
        openAction.setOpen(openCriteria);

        clickAction = new ClickAction();
        FilterCriteria clickCriteria = new FilterCriteria();
        clickCriteria.setSelector("a[title=\"About\"]");
        clickAction.setClick(clickCriteria);

        ensureAction = new EnsureAction();
        FilterCriteria ensureCriteria = new FilterCriteria();
        ensureCriteria.setSelector("#About");
        ensureAction.setEnsure(ensureCriteria);
    }

    @Test
    public void simpleActionsShouldBeExecuted() {
        Scenario scenario = new Scenario();
        scenario.setName("simpleScenario");
        scenario.setMeta("data");
        scenario.setActions(Arrays.<Action>asList(openAction, clickAction, ensureAction));

        scenarioExecutor.execute(asContext(Arrays.asList(scenario)));
    }

    @Test
    public void clickByDefaultSelectorShouldWork() {
        List<Scenario> scenarios = getScenarios(PACKAGE + "/executor/click-by-selector-should-work.yaml");

        scenarioExecutor.execute(asContext(scenarios));
    }

    @Test
    public void ensureElementStoredBySelectorAndText() {
        List<Scenario> scenarios = getScenarios(PACKAGE + "/executor/ensure-element-stored-by-selector-and-text.yaml");

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
    public void clickByParentSelectorAndTextShouldWork() {
        List<Scenario> scenarios = getScenarios(PACKAGE + "/executor/click-by-parent-selector-and-text-should-work.yaml");

        scenarioExecutor.execute(asContext(scenarios));
    }

    @Test
    public void clickBySelectorAndTextShouldWork() {
        List<Scenario> scenarios = getScenarios(PACKAGE + "/executor/click-by-partial-text-and-selector-should-work.yaml");

        scenarioExecutor.execute(asContext(scenarios));
    }

    @Test
    public void disabledConditionalScenarioIsNotExecuted() {
        List<Scenario> scenarios = getScenarios(PACKAGE + "/executor/included-disabled-conditional-scenario-is-not-executed.yaml");

        scenarioExecutor.execute(asContext(scenarios));
    }

    @Test
    public void includedDisabledConditionalScenarioIsNotExecuted() {
        List<Scenario> scenarios = getScenarios(PACKAGE + "/executor/disabled-conditional-scenario-is-not-executed.yaml");

        scenarioExecutor.execute(asContext(scenarios));
    }

    @Test
    public void timeoutPropertyOnScenarioIsPreprocessed() {
        List<Scenario> scenarios = getScenarios(PACKAGE + "/executor/timeout-property-on-scenario-is-preprocessed.yaml");
        GlobalExecutionContext context = asContext(scenarios, Collections.singletonMap("timeout", (Object)"1"));

        scenarioExecutor.execute(context);
    }

    @Test(expected = RecursiveScenarioInclusionException.class)
    public void shouldDetectCircularDependency() {
        InputStream stream = getSystemResourceAsStream("./failing_scenarios/circular-dependency.yaml");
        List<Scenario> scenarios = scenarioLoader.createFromInputStream(stream);

        scenarioExecutor.execute(asContext(scenarios));
    }

    @Test
    public void ensureAbsentWorks() {
        List<Scenario> scenarios = getScenarios(PACKAGE + "/executor/ensure-absent-works.yaml");

        scenarioExecutor.execute(asContext(scenarios));
    }

    @Test
    public void cmdContextIsPreferred() {
        List<Scenario> scenarios = getScenarios(PACKAGE + "/executor/cmd-context-is-preferred.yaml");

        scenarioExecutor.execute(asContext(scenarios, singletonMap("url", (Object)"http://localhost:8089")));
    }

    @Test
    public void shouldHandleAlert() {
        List<Scenario> scenarios = getScenarios(PACKAGE + "/executor/alert-alert-should-be-dismissed.yaml");

        scenarioExecutor.execute(asContext(scenarios));
    }

    @Test
    public void shouldHandleConfirm() {
        List<Scenario> scenarios = getScenarios(PACKAGE + "/executor/alert-confirm-should-be-dismissed.yaml");

        scenarioExecutor.execute(asContext(scenarios));
    }

    @Test
    public void shouldHandlePrompt() {
        List<Scenario> scenarios = getScenarios(PACKAGE + "/executor/alert-prompt-should-be-dismissed.yaml");

        scenarioExecutor.execute(asContext(scenarios));
    }

    private GlobalExecutionContext asContext(List<Scenario> scenarios){
        return asContext(scenarios, new HashMap<String, Object>());
    }

    private GlobalExecutionContext asContext(List<Scenario> scenarios, Map<String, Object> memory){
        return new GlobalExecutionContext(asScenarioFiles(scenarios), ExecutorOptions.of(new ConfigurationProperties()), memory);
    }

    private List<ScenarioFile> asScenarioFiles(List<Scenario> scenarios){
        return Arrays.asList(new ScenarioFile(scenarios, null));
    }

    private List<Scenario> getScenarios(String scenarioPath) {
        return scenarioLoader
            .load(asList(scenarioPath), ConfigurationProperties.DEFAULT_REPORT_PATH)
            .get(0)
            .getScenarios();
    }
}
