package website.automate.jwebrobot.models.mapper;

import com.google.inject.Inject;

import website.automate.jwebrobot.models.scenario.Scenario;
import website.automate.jwebrobot.models.scenario.actions.Action;
import website.automate.jwebrobot.utils.CollectionMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class ScenarioMapper extends CollectionMapper<Object, Scenario> {
    private static final String NAME = "name";
    private static final String STEPS = "steps";

    @Inject
    private StepsMapper stepsMapper;

    @Override
    public Scenario map(Object source) {
        Map<String, Object> sourceScenario = (Map<String, Object>) source;

        Scenario scenario = new Scenario();
        scenario.setName(String.valueOf(sourceScenario.get(NAME)));

        List<Object> stepList = (ArrayList) sourceScenario.get(STEPS);
        List<Action> actions = stepsMapper.map(stepList);
        scenario.setSteps(actions);

        return scenario;
    }


    public void map(Object source, Scenario target) {
        // TODO
    }

}
