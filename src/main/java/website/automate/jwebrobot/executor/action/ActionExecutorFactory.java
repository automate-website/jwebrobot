package website.automate.jwebrobot.executor.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import website.automate.jwebrobot.exceptions.ActionExecutorMissingException;
import website.automate.waml.io.model.main.action.Action;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
public class ActionExecutorFactory {

    private final Map<Class<? extends Action>, ActionExecutor<? extends Action>> executorRegistry = new HashMap<>();

    private final Set<ActionExecutor<? extends Action>> executors;

    @Autowired
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
