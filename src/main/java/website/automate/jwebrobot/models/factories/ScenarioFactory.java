package website.automate.jwebrobot.models.factories;


import org.slf4j.Logger;
import org.yaml.snakeyaml.Yaml;

import website.automate.jwebrobot.config.logger.InjectLogger;
import website.automate.jwebrobot.model.Scenario;
import website.automate.jwebrobot.model.mapper.ScenarioMapper;

import javax.inject.Inject;

import java.io.InputStream;
import java.util.List;

public class ScenarioFactory {

    @InjectLogger
    private Logger logger;

    private final ScenarioMapper scenarioMapper;

    @Inject
    public ScenarioFactory(ScenarioMapper scenarioMapper) {
        this.scenarioMapper = scenarioMapper;
    }

    public List<Scenario> createFromInputStream(InputStream inputStream) {
        Yaml yaml = new Yaml();

        Iterable<Object> objects = yaml.loadAll(inputStream);

        List<Scenario> scenarios = scenarioMapper.map(objects);

        logger.info("Loaded {} scenarios.", scenarios.size());

        return scenarios;
    }

}
