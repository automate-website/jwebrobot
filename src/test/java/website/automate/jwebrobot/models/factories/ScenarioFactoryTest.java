package website.automate.jwebrobot.models.factories;


import org.junit.Test;

import website.automate.jwebrobot.AbstractTest;
import website.automate.jwebrobot.exceptions.TooManyActionsException;
import website.automate.jwebrobot.exceptions.UnknownActionException;
import website.automate.jwebrobot.exceptions.UnknownCriterionException;
import website.automate.jwebrobot.models.scenario.Scenario;
import website.automate.jwebrobot.models.scenario.actions.Action;
import website.automate.jwebrobot.models.scenario.actions.ClickAction;
import website.automate.jwebrobot.models.scenario.actions.OpenAction;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static java.lang.ClassLoader.getSystemResourceAsStream;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class ScenarioFactoryTest extends AbstractTest {


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

    @Test(expected = UnknownCriterionException.class)
    public void shouldThrowUnknownCriterionException() {
        InputStream stream = getSystemResourceAsStream("./failing_scenarios/unknown-criterion.yaml");

        scenarioFactory.createFromInputStream(stream);
    }

    @Test
    public void shouldSupportComplexCriteria() {
        // given
        InputStream inputStream = getSystemResourceAsStream("./scenarios/complex-criteria.yaml");

        // when
        Scenario scenario = scenarioFactory.createFromInputStream(inputStream).get(0);

        // then
        assertThat(((OpenAction) scenario.getSteps().get(0)).getUrl().getValue(), is("www.example.com"));
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
