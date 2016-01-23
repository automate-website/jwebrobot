package website.automate.jwebrobot.executor;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import website.automate.jwebrobot.AbstractTest;
import website.automate.jwebrobot.models.factories.ScenarioFactory;
import website.automate.jwebrobot.models.scenario.Scenario;
import website.automate.jwebrobot.models.scenario.actions.Action;
import website.automate.jwebrobot.models.scenario.actions.ClickAction;
import website.automate.jwebrobot.models.scenario.actions.EnsureAction;
import website.automate.jwebrobot.models.scenario.actions.OpenAction;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import static java.lang.ClassLoader.getSystemResourceAsStream;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

public class ScenarioExecutorTest extends AbstractTest {

    private ScenarioExecutor scenarioExecutor;
    private ContextHolder contextHolder;
    private ExecutorOptions executorOptions;

    private OpenAction openAction;
    private ClickAction clickAction;
    private EnsureAction ensureAction;
    private ScenarioFactory scenarioFactory;

    @Before
    public void setUp() {
        contextHolder = new ContextHolder();
        executorOptions = new ExecutorOptions();

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
    @Ignore
    public void scenariosShouldBeSorted() {
        Scenario scenario1 = new Scenario();
        scenario1.setPrecedence(-5);

        Scenario scenario2 = new Scenario();
        scenario2.setPrecedence(100);



        List<Scenario> scenarios = Arrays.asList(scenario1, scenario2);
        scenarioExecutor.execute(scenarios, contextHolder, executorOptions);

        assertThat(scenarios, hasSize(2));
        assertThat(scenarios.get(0), is(scenario2));
        assertThat(scenarios.get(1), is(scenario1));
    }

    @Test
    public void simpleActionsShouldBeExecuted() {
        Scenario scenario = new Scenario();
        scenario.setSteps(Arrays.<Action>asList(openAction, clickAction, ensureAction));

        scenarioExecutor.execute(Arrays.asList(scenario), contextHolder, executorOptions);
    }

    @Test
    public void simpleActionsShouldBeExecuted2() {
        InputStream stream = getSystemResourceAsStream("./scenarios/wikipedia-test.yaml");
        List<Scenario> scenarios = scenarioFactory.createFromInputStream(stream);

        scenarioExecutor.execute(scenarios, contextHolder, executorOptions);
    }
}
