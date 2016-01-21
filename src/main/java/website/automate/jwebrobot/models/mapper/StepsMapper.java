package website.automate.jwebrobot.models.mapper;

import com.google.inject.Inject;

import website.automate.jwebrobot.exceptions.TooManyActionsException;
import website.automate.jwebrobot.exceptions.UnknownActionException;
import website.automate.jwebrobot.models.mapper.actions.ActionMapper;
import website.automate.jwebrobot.models.scenario.actions.Action;
import website.automate.jwebrobot.utils.CollectionMapper;

import java.util.Map;
import java.util.Set;


public class StepsMapper extends CollectionMapper<Object, Action> {

    @Inject
    private Set<ActionMapper> actionMappers;

    @Override
    public Action map(Object source) {
        Action action;

        Map<String, Object> actionsMap = (Map<String, Object>) source;

        if (actionsMap.keySet().size() != 1) {
            throw new TooManyActionsException(actionsMap.keySet().size());
        }

        String actionName = actionsMap.keySet().iterator().next();

        ActionMapper<Action> actionMapper = findActionMapper(actionName);

        if (actionMapper == null) {
            throw new UnknownActionException(actionName);
        }

        Object actionMap = actionsMap.get(actionName);
        action = actionMapper.map(actionMap);

        return action;
    }

    private ActionMapper findActionMapper(String actionName) {
        for (ActionMapper actionMapper: actionMappers) {
            if (actionMapper.getActionName().equalsIgnoreCase(actionName)) {
                return actionMapper;
            }
        }

        return null;
    }

    @Override
    public void map(Object source, Action target) {
        // NOP
    }

}
