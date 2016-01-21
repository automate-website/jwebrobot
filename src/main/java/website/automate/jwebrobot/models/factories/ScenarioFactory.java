package website.automate.jwebrobot.models.factories;


import com.google.inject.Inject;

import org.yaml.snakeyaml.Yaml;

import website.automate.jwebrobot.models.mapper.ScenarioMapper;
import website.automate.jwebrobot.models.scenario.Scenario;

import java.io.InputStream;
import java.util.List;

public class ScenarioFactory {

    @Inject
    private ScenarioMapper scenarioMapper;

    public List<Scenario> createFromInputStream(InputStream inputStream) {
        Yaml yaml = new Yaml();

        Iterable<Object> objects = yaml.loadAll(inputStream);

        List<Scenario> scenarios = scenarioMapper.map(objects);

        return scenarios;
    }

}
