package website.automate.jwebrobot.model.mapper;

import org.junit.Before;
import org.junit.Test;
import org.yaml.snakeyaml.Yaml;

import website.automate.jwebrobot.AbstractTest;
import website.automate.jwebrobot.exceptions.UnknownMetadataException;
import website.automate.jwebrobot.model.Scenario;
import website.automate.jwebrobot.model.mapper.ScenarioMapper;

import java.io.InputStream;

import static java.lang.ClassLoader.getSystemResourceAsStream;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ScenarioMapperTest extends AbstractTest {

    private ScenarioMapper scenarioMapper;
    private Yaml yaml;

    @Before
    public void setUp() {
        scenarioMapper = injector.getInstance(ScenarioMapper.class);
        yaml = new Yaml();
    }

    @Test
    public void metadataShouldBeParsed() {
        InputStream inputStream = getSystemResourceAsStream("./scenarios/scenario-mapper-test-full.yaml");
        Yaml yaml = new Yaml();
        Object objects = yaml.load(inputStream);

        Scenario scenario = scenarioMapper.map(objects);

        assertThat(scenario.getName(), is("full-featured-scenario"));
        assertThat(scenario.getDescription(), is("A full featured scenario"));
        assertThat(scenario.isFragment(), is(true));
        assertThat(scenario.getPrecedence(), is(1));
        assertThat(scenario.getTimeout(), is("2000"));
        assertThat(scenario.getIf(), is("${true}"));
        assertThat(scenario.getUnless(), is("${false}"));
    }

    @Test(expected = UnknownMetadataException.class)
    public void unknownPropertyShowRaiseException() {
        InputStream inputStream = getSystemResourceAsStream("./scenarios/scenario-mapper-test-fail.yaml");
        Object objects = yaml.load(inputStream);

        scenarioMapper.map(objects);
    }
}
