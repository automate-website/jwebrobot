package website.automate.jwebrobot.executor;

import org.junit.Before;
import org.junit.Test;
import website.automate.jwebrobot.AbstractTest;
import website.automate.jwebrobot.models.scenario.Scenario;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

public class ScenarioExecutorTest extends AbstractTest {

    private Scenario scenario1, scenario2;

    private ScenarioExecutor scenarioExecutor;
    private ContextHolder contextHolder;
    private ExecutorOptions executorOptions;

    @Before
    public void setUp() {
        contextHolder = new ContextHolder();
        executorOptions = new ExecutorOptions();

        scenarioExecutor = injector.getInstance(ScenarioExecutor.class);

        scenario1 = new Scenario();
        scenario1.setPrecedence(-5);

        scenario2 = new Scenario();
        scenario2.setPrecedence(100);

    }

    @Test
    public void executorShouldSortScenarios() {
        List<Scenario> scenarios = Arrays.asList(scenario1, scenario2);
        scenarioExecutor.execute(scenarios, contextHolder, executorOptions);

        assertThat(scenarios, hasSize(2));
        assertThat(scenarios.get(0), is(scenario2));
        assertThat(scenarios.get(1), is(scenario1));
    }
}
