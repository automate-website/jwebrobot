package website.automate.jwebrobot.executor.action;

import website.automate.jwebrobot.exceptions.ActionExecutorMissingException;
import website.automate.waml.io.model.action.Action;

import javax.inject.Inject;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ActionExecutorFactory {

    private final Map<Class<? extends Action>, ActionExecutor<? extends Action>> executorRegistry = new HashMap<>();

    private final Set<ActionExecutor<? extends Action>> executors;

    @Inject
    public ActionExecutorFactory(Set<ActionExecutor<? extends Action>> executors) {
        this.executors = executors;

        init();
    }

    private void init() {
        for (ActionExecutor<? extends Action> executor : executors) {
            executorRegistry.put(executor.getSupportedType(), executor);
        }
    }

    @SuppressWarnings("unchecked")
    public ActionExecutor<Action> getInstance(Class<? extends Action> actionClazz) {
        ActionExecutor<? extends Action> actionExecutor = executorRegistry.get(actionClazz);

        if (actionExecutor == null) {
            throw new ActionExecutorMissingException(actionClazz);
        }

        return (ActionExecutor<Action>)actionExecutor;
    }
}
