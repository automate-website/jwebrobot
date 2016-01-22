package website.automate.jwebrobot.models.factories;


import org.yaml.snakeyaml.Yaml;
import website.automate.jwebrobot.models.mapper.ScenarioMapper;
import website.automate.jwebrobot.models.scenario.Scenario;

import javax.inject.Inject;
import java.io.InputStream;
import java.util.List;

public class ScenarioFactory {

    private final ScenarioMapper scenarioMapper;

    @Inject
    public ScenarioFactory(ScenarioMapper scenarioMapper) {
        this.scenarioMapper = scenarioMapper;
    }

    public List<Scenario> createFromInputStream(InputStream inputStream) {
        Yaml yaml = new Yaml();

        Iterable<Object> objects = yaml.loadAll(inputStream);

        List<Scenario> scenarios = scenarioMapper.map(objects);

        return scenarios;
    }

}
