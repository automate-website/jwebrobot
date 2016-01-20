package website.automate.executors.jwebrobot;

import com.google.inject.Injector;
import org.junit.Before;
import website.automate.executors.jwebrobot.models.factories.ScenarioFactory;

public abstract class AbstractTest {
    protected ScenarioFactory scenarioFactory;

    @Before
    public void setUp() {
        Injector injector = JWebRobot.configureModules();

        scenarioFactory = injector.getInstance(ScenarioFactory.class);
    }
}
