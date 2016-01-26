package website.automate.jwebrobot.model.mapper;

import website.automate.jwebrobot.exceptions.UnknownMetadataException;
import website.automate.jwebrobot.model.Action;
import website.automate.jwebrobot.model.Scenario;
import website.automate.jwebrobot.utils.CollectionMapper;

import javax.inject.Inject;

import java.util.*;


public class ScenarioMapper extends CollectionMapper<Object, Scenario> {
    private static final String NAME = "name";
    private static final String STEPS = "steps";
    private static final String DOLLAR_SCHEMA = "$schema";
    private static final String DESCRIPTION = "description";
    private static final String PRECEDENCE = "precedence";
    private static final String FRAGMENT = "fragment";
    private static final String TIMEOUT = "timeout";
    private static final String IF = "if";
    private static final String UNLESS = "unless";

    private final static Set<String> ALLOWED_PROPERTIES =  new HashSet<String>(
        Arrays.asList(NAME, STEPS, DOLLAR_SCHEMA, DESCRIPTION, PRECEDENCE, FRAGMENT, TIMEOUT, IF, UNLESS));

    private final StepsMapper stepsMapper;

    @Inject
    public ScenarioMapper(StepsMapper stepsMapper) {
        this.stepsMapper = stepsMapper;
    }

    @Override
    public Scenario map(Object source) {
        Scenario scenario = new Scenario();

        map(source, scenario);

        return scenario;
    }

    @SuppressWarnings("unchecked")
    public void map(Object source, Scenario target) {
        Map<String, Object> sourceScenario = (Map<String, Object>) source;
        verifyProperties(sourceScenario.keySet());

        target.setName(String.valueOf(sourceScenario.get(NAME)));

        List<Object> stepList = (List<Object>) sourceScenario.get(STEPS);
        List<Action> actions = stepsMapper.map(stepList);
        target.setSteps(actions);

        if (sourceScenario.get(DESCRIPTION) != null) {
            target.setDescription(String.valueOf(sourceScenario.get(DESCRIPTION)));
        }

        if (sourceScenario.get(PRECEDENCE) != null) {
            target.setPrecedence((int) sourceScenario.get(PRECEDENCE));
        }

        if (sourceScenario.get(FRAGMENT) != null) {
            target.setFragment((boolean) sourceScenario.get(FRAGMENT));
        }

        if (sourceScenario.get(TIMEOUT) != null) {
            target.setTimeout(String.valueOf(sourceScenario.get(TIMEOUT)));
        }

        if (sourceScenario.get(IF) != null) {
            target.setIf(String.valueOf(sourceScenario.get(IF)));
        }

        if (sourceScenario.get(UNLESS) != null) {
            target.setUnless(String.valueOf(sourceScenario.get(UNLESS)));
        }
    }

    private void verifyProperties(Set<String> properties) {
        for (String metadata : properties) {
            if (!ALLOWED_PROPERTIES.contains(metadata.toLowerCase())) {
                throw new UnknownMetadataException(metadata);
            }
        }
    }

}
