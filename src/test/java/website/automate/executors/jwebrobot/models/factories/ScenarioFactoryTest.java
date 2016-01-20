package website.automate.executors.jwebrobot.models.factories;


import com.google.inject.Injector;
import org.junit.Before;
import org.junit.Test;
import website.automate.executors.jwebrobot.JWebRobot;
import website.automate.executors.jwebrobot.models.scenario.Scenario;
import website.automate.executors.jwebrobot.models.scenario.actions.Action;
import website.automate.executors.jwebrobot.models.scenario.actions.ClickAction;
import website.automate.executors.jwebrobot.models.scenario.actions.OpenAction;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class ScenarioFactoryTest {

    private InputStream multiDocumentInputStream;

    private ScenarioFactory scenarioFactory;

    @Before
    public void setUp() {
        multiDocumentInputStream = ClassLoader.getSystemResourceAsStream("./scenarios/multi-document.yaml");

        Injector injector = JWebRobot.configureModules();

        scenarioFactory = injector.getInstance(ScenarioFactory.class);
    }

    @Test
    public void scenarioListShouldBeLoaded() throws IOException {
        // given

        // when
        List<Scenario> scenarioList = scenarioFactory.createFromInputStream(multiDocumentInputStream);

        // then
        assertThat(scenarioList, hasSize(2));

        Scenario scenario1 = scenarioList.get(0),
                scenario2 = scenarioList.get(1);

        assertThat(scenario1.getName(), is("Scenario A"));
        assertThat(scenario2.getName(), is("Scenario B"));

        List<Action> steps1 = scenario1.getSteps();
        assertThat(steps1, hasSize(2));
        List<Action> steps2 = scenario2.getSteps();
        assertThat(steps2, hasSize(3));

        assertThat(steps1.get(0), instanceOf(OpenAction.class));
        assertThat(steps1.get(1), instanceOf(ClickAction.class));

        assertThat(steps2.get(0), instanceOf(OpenAction.class));
        assertThat(steps2.get(1), instanceOf(OpenAction.class));
        assertThat(steps2.get(2), instanceOf(ClickAction.class));

        assertThat(((OpenAction)steps1.get(0)).getUrl().getValue(), is("www.example.com"));
        assertThat(((ClickAction)steps1.get(1)).getSelector().getValue(), is("button[type=submit]"));

        assertThat(((OpenAction)steps2.get(0)).getUrl().getValue(), is("www.example.com"));
        assertThat(((OpenAction)steps2.get(1)).getUrl().getValue(), is("www.example2.com"));
        assertThat(((ClickAction)steps2.get(2)).getSelector().getValue(), is("button[type=submit2]"));

    }
}
