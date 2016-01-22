package website.automate.executors.jwebrobot.executor;

import com.google.inject.Injector;
import org.junit.Before;
import org.junit.Test;
import website.automate.jwebrobot.AbstractTest;
import website.automate.jwebrobot.JWebRobot;
import website.automate.jwebrobot.models.scenario.Scenario;
import website.automate.jwebrobot.executor.ScenarioExecutor;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

public class ScenarioExecutorTest extends AbstractTest {

    private Scenario scenario1, scenario2;

    private ScenarioExecutor scenarioExecutor;

    @Before
    public void setUp() {
        Injector injector = JWebRobot.configureModules();

        scenarioExecutor = injector.getInstance(ScenarioExecutor.class);

        scenario1 = new Scenario();
        scenario1.setPrecedence(-5);

        scenario2 = new Scenario();
        scenario2.setPrecedence(100);

    }

    @Test
    public void executorShouldSortScenarios() {
        List<Scenario> scenarios = Arrays.asList(scenario1, scenario2);
        scenarioExecutor.execute(scenarios, null, null);

        assertThat(scenarios, hasSize(2));
        assertThat(scenarios.get(0), is(scenario2));
        assertThat(scenarios.get(1), is(scenario1));
    }
}
