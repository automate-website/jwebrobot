package website.automate.jwebrobot.executor.action;

import website.automate.jwebrobot.exceptions.ActionExecutorMissingException;
import website.automate.jwebrobot.model.ActionType;

import javax.inject.Inject;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ActionExecutorFactory {

    private final Map<ActionType, ActionExecutor> executorRegistry = new HashMap<>();

    private final Set<ActionExecutor> executors;

    @Inject
    public ActionExecutorFactory(Set<ActionExecutor> executors) {
        this.executors = executors;

        init();
    }

    private void init() {
        for (ActionExecutor executor : executors) {
            executorRegistry.put(executor.getActionType(), executor);
        }
    }

    public ActionExecutor getInstance(ActionType actionType) {
        ActionExecutor actionExecutor = executorRegistry.get(actionType);

        if (actionExecutor == null) {
            throw new ActionExecutorMissingException(actionType);
        }

        return actionExecutor;
    }
}
