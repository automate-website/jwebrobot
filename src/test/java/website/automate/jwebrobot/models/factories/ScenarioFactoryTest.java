package website.automate.jwebrobot.models.factories;


import org.junit.Before;
import org.junit.Test;

import website.automate.jwebrobot.AbstractTest;
import website.automate.jwebrobot.exceptions.TooManyActionsException;
import website.automate.jwebrobot.exceptions.UnknownActionException;
import website.automate.jwebrobot.model.Action;
import website.automate.jwebrobot.model.Scenario;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static java.lang.ClassLoader.getSystemResourceAsStream;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class ScenarioFactoryTest extends AbstractTest {

    protected ScenarioFactory scenarioFactory;

    @Before
    public void setUp() {
        scenarioFactory = injector.getInstance(ScenarioFactory.class);
    }

    @Test(expected = UnknownActionException.class)
    public void shouldThrowUnknownActionException() {
        InputStream stream = getSystemResourceAsStream("./failing_scenarios/unknown-action.yaml");

        scenarioFactory.createFromInputStream(stream);
    }

    @Test(expected = TooManyActionsException.class)
    public void shouldThrowTooManyActionsException() {
        InputStream stream = getSystemResourceAsStream("./failing_scenarios/too-many-actions.yaml");

        scenarioFactory.createFromInputStream(stream);
    }

    @Test
    public void scenarioListShouldBeLoaded() throws IOException {
        // given
        InputStream inputStream = getSystemResourceAsStream("./scenarios/multi-document.yaml");

        // when
        List<Scenario> scenarioList = scenarioFactory.createFromInputStream(inputStream);

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

        assertThat(steps1.get(0).getUrl(), is("www.example.com"));
        assertThat(steps1.get(1).getSelector(), is("button[type=submit]"));

        assertThat(steps2.get(0).getUrl(), is("www.example.com"));
        assertThat(steps2.get(1).getUrl(), is("www.example2.com"));
        assertThat(steps2.get(2).getSelector(), is("button[type=submit2]"));

    }
}
