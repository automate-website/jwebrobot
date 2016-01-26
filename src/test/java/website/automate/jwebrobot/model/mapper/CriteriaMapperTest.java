package website.automate.jwebrobot.model.mapper;

import org.junit.Before;
import org.junit.Test;

import website.automate.jwebrobot.AbstractTest;
import website.automate.jwebrobot.model.Action;
import website.automate.jwebrobot.model.Scenario;
import website.automate.jwebrobot.models.factories.ScenarioFactory;

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
        Action action = steps.get(0);

        assertThat(action.getUrl(), is("open.url1"));
        assertThat(action.getIf(), nullValue());
        assertThat(action.getUnless(), nullValue());
    }

    @Test
    public void shouldSupportComplexOpenAction() {
        Action action = steps.get(1);

        assertThat(action.getUrl(), is("open.url"));
        assertThat(action.getIf(), is("open.if"));
        assertThat(action.getUnless(), is("open.unless"));
    }

    @Test
    public void shouldSupportSimpleEnsureAction() {
        Action action = steps.get(2);

        assertThat(action.getSelector(), is("ensure.selector1"));
    }

    @Test
    public void shouldSupportComplexEnsureAction() {
        Action action = steps.get(3);

        assertThat(action.getSelector(), is("ensure.selector"));
        assertThat(action.getText(), is("ensure.text"));
        assertThat(action.getTimeout(), is("ensure.timeout"));
        assertThat(action.getValue(), is("ensure.value"));
        assertThat(action.getIf(), is("ensure.if"));
        assertThat(action.getUnless(), is("ensure.unless"));
    }

    @Test
    public void shouldSupportSimpleMoveAction() {
        Action action = steps.get(4);

        assertThat(action.getSelector(), is("move.selector1"));
    }

    @Test
    public void shouldSupportComplexMoveAction() {
        Action action = steps.get(5);

        assertThat(action.getSelector(), is("move.selector"));
        // TODO: Add other criteria check
        assertThat(action.getIf(), is("move.if"));
        assertThat(action.getUnless(), is("move.unless"));
    }

    @Test
    public void shouldSupportSimpleClickAction() {
        Action action = steps.get(6);

        assertThat(action.getSelector(), is("click.selector1"));
    }

    @Test
    public void shouldSupportComplexClickAction() {
        Action action = steps.get(7);

        assertThat(action.getSelector(), is("click.selector"));
        // TODO: Add other criteria check
        assertThat(action.getIf(), is("click.if"));
        assertThat(action.getUnless(), is("click.unless"));
    }

    @Test
    public void shouldSupportSimpleSelectAction() {
        Action action = steps.get(8);

        assertThat(action.getSelector(), is("select.selector1"));
    }

    @Test
    public void shouldSupportComplexSelectAction() {
        Action action = steps.get(9);

        assertThat(action.getSelector(), is("select.selector"));
        assertThat(action.getValue(), is("select.value"));
        // TODO: Add other criteria check
        assertThat(action.getIf(), is("select.if"));
        assertThat(action.getUnless(), is("select.unless"));
    }

    @Test
    public void shouldSupportSimpleEnterAction() {
        Action action = steps.get(10);

        assertThat(action.getValue(), is("enter.value1"));
    }

    @Test
    public void shouldSupportComplexEnterAction() {
        Action action = steps.get(11);

        assertThat(action.getValue(), is("enter.value"));
        assertThat(action.getClear(), is(true));
        // TODO: Add other criteria check
        assertThat(action.getIf(), is("enter.if"));
        assertThat(action.getUnless(), is("enter.unless"));
    }


    @Test
    public void shouldSupportSimpleWaitAction() {
        Action action = steps.get(12);

        assertThat(action.getTime(), is("wait.time1"));
    }

    @Test
    public void shouldSupportComplexWaitAction() {
        Action action = steps.get(13);;

        assertThat(action.getTime(), is("wait.time"));
        // TODO: Add other criteria check
        assertThat(action.getIf(), is("wait.if"));
        assertThat(action.getUnless(), is("wait.unless"));
    }

    @Test
    public void shouldSupportSimpleIncludeAction() {
        Action action = steps.get(14);

        assertThat(action.getScenario(), is("include.scenario1"));
    }

    @Test
    public void shouldSupportComplexIncludeAction() {
        Action action = steps.get(15);

        assertThat(action.getScenario(), is("include.scenario"));
        // TODO: Add other criteria check
        assertThat(action.getIf(), is("include.if"));
        assertThat(action.getUnless(), is("include.unless"));
    }
}
