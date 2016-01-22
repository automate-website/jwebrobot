package website.automate.jwebrobot.models.mapper.actions;

import website.automate.jwebrobot.exceptions.UnknownActionException;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Set;

public class ActionMapperFactory {
    private final Set<ActionMapper> actionMappers;
    private HashMap<String, ActionMapper> actionMapperByActionNameMap = new HashMap<>();

    @Inject
    public ActionMapperFactory(Set<ActionMapper> actionMappers) {
        this.actionMappers = actionMappers;
        init();
    }

    private void init() {
        for (ActionMapper actionMapper : actionMappers) {
            actionMapperByActionNameMap.put(actionMapper.getActionName().toLowerCase(), actionMapper);
        }
    }

    public ActionMapper getInstance(String actionName) {
        ActionMapper actionMapper = actionMapperByActionNameMap.get(actionName.toLowerCase());
        if (actionMapper == null) {
            throw new UnknownActionException(actionName);
        }

        return actionMapper;
    }
}
