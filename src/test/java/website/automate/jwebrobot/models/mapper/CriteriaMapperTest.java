package website.automate.jwebrobot.models.mapper;

import org.junit.Before;
import org.junit.Test;
import website.automate.jwebrobot.AbstractTest;
import website.automate.jwebrobot.models.factories.ScenarioFactory;
import website.automate.jwebrobot.models.scenario.Scenario;
import website.automate.jwebrobot.models.scenario.actions.*;

import java.io.InputStream;
import java.util.List;

import static java.lang.ClassLoader.getSystemResourceAsStream;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;


public class CriteriaMapperTest extends AbstractTest {
    protected ScenarioFactory scenarioFactory;
    private List<Action> steps;

    @Before
    public void setUp() {
        scenarioFactory = injector.getInstance(ScenarioFactory.class);

        // given
        InputStream inputStream = getSystemResourceAsStream("./scenarios/all-criteria.yaml");

        // when
        Scenario scenario = scenarioFactory.createFromInputStream(inputStream).get(0);

        steps = scenario.getSteps();
    }

    @Test
    public void shouldSupportSimpleOpenAction() {
        OpenAction action = (OpenAction) steps.get(0);

        assertThat(action.getUrl().getValue(), is("open.url1"));
        assertThat(action.getIf(), nullValue());
        assertThat(action.getUnless(), nullValue());
    }

    @Test
    public void shouldSupportComplexOpenAction() {
        OpenAction action = (OpenAction) steps.get(1);

        assertThat(action.getUrl().getValue(), is("open.url"));
        assertThat(action.getIf().getValue(), is("open.if"));
        assertThat(action.getUnless().getValue(), is("open.unless"));
    }

    @Test
    public void shouldSupportSimpleEnsureAction() {
        EnsureAction action = (EnsureAction) steps.get(2);

        assertThat(action.getSelector().getValue(), is("ensure.selector1"));
    }

    @Test
    public void shouldSupportComplexEnsureAction() {
        EnsureAction action = (EnsureAction) steps.get(3);

        assertThat(action.getSelector().getValue(), is("ensure.selector"));
        assertThat(action.getText().getValue(), is("ensure.text"));
        assertThat(action.getTimeout().getValue(), is("ensure.timeout"));
        assertThat(action.getValue().getValue(), is("ensure.value"));
        assertThat(action.getIf().getValue(), is("ensure.if"));
        assertThat(action.getUnless().getValue(), is("ensure.unless"));
    }

    @Test
    public void shouldSupportSimpleMoveAction() {
        MoveAction action = (MoveAction) steps.get(4);

        assertThat(action.getSelector().getValue(), is("move.selector1"));
    }

    @Test
    public void shouldSupportComplexMoveAction() {
        MoveAction action = (MoveAction) steps.get(5);

        assertThat(action.getSelector().getValue(), is("move.selector"));
        // TODO: Add other criteria check
        assertThat(action.getIf().getValue(), is("move.if"));
        assertThat(action.getUnless().getValue(), is("move.unless"));
    }

    @Test
    public void shouldSupportSimpleClickAction() {
        ClickAction action = (ClickAction) steps.get(6);

        assertThat(action.getSelector().getValue(), is("click.selector1"));
    }

    @Test
    public void shouldSupportComplexClickAction() {
        ClickAction action = (ClickAction) steps.get(7);

        assertThat(action.getSelector().getValue(), is("click.selector"));
        // TODO: Add other criteria check
        assertThat(action.getIf().getValue(), is("click.if"));
        assertThat(action.getUnless().getValue(), is("click.unless"));
    }

    @Test
    public void shouldSupportSimpleSelectAction() {
        SelectAction action = (SelectAction) steps.get(8);

        assertThat(action.getSelector().getValue(), is("select.selector1"));
    }

    @Test
    public void shouldSupportComplexSelectAction() {
        SelectAction action = (SelectAction) steps.get(9);

        assertThat(action.getSelector().getValue(), is("select.selector"));
        assertThat(action.getValue().getValue(), is("select.value"));
        // TODO: Add other criteria check
        assertThat(action.getIf().getValue(), is("select.if"));
        assertThat(action.getUnless().getValue(), is("select.unless"));
    }

    @Test
    public void shouldSupportSimpleEnterAction() {
        EnterAction action = (EnterAction) steps.get(10);

        assertThat(action.getValue().getValue(), is("enter.value1"));
    }

    @Test
    public void shouldSupportComplexEnterAction() {
        EnterAction action = (EnterAction) steps.get(11);

        assertThat(action.getValue().getValue(), is("enter.value"));
        assertThat(action.getClear().getValue(), is(true));
        // TODO: Add other criteria check
        assertThat(action.getIf().getValue(), is("enter.if"));
        assertThat(action.getUnless().getValue(), is("enter.unless"));
    }


    @Test
    public void shouldSupportSimpleWaitAction() {
        WaitAction action = (WaitAction) steps.get(12);

        assertThat(action.getTime().getValue(), is("wait.time1"));
    }

    @Test
    public void shouldSupportComplexWaitAction() {
        WaitAction action = (WaitAction) steps.get(13);

        assertThat(action.getTime().getValue(), is("wait.time"));
        // TODO: Add other criteria check
        assertThat(action.getIf().getValue(), is("wait.if"));
        assertThat(action.getUnless().getValue(), is("wait.unless"));
    }

    @Test
    public void shouldSupportSimpleIncludeAction() {
        IncludeAction action = (IncludeAction) steps.get(14);

        assertThat(action.getScenario().getValue(), is("include.scenario1"));
    }

    @Test
    public void shouldSupportComplexIncludeAction() {
        IncludeAction action = (IncludeAction) steps.get(15);

        assertThat(action.getScenario().getValue(), is("include.scenario"));
        // TODO: Add other criteria check
        assertThat(action.getIf().getValue(), is("include.if"));
        assertThat(action.getUnless().getValue(), is("include.unless"));
    }
}
