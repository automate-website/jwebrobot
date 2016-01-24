package website.automate.jwebrobot.executor.action;

import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.exceptions.UnsupportedActionException;
import website.automate.jwebrobot.models.scenario.actions.Action;
import static java.text.MessageFormat.format;

public abstract class BaseActionExecutor<T extends Action> implements ActionExecutor<T> {

    @SuppressWarnings("unchecked")
    @Override
    public void execute(Action action, ScenarioExecutionContext context){
        Class<? extends Action> actualActionType = action.getClass();
        Class<? extends Action> actionType = getActionType();
        if(!getActionType().isAssignableFrom(actualActionType)){
            throw new UnsupportedActionException(format("Given action type {0} is not compatible with supported type {1}.", actualActionType, actionType));
        }
        safeExecute((T)action, context);
    }

    public abstract void safeExecute(T action, ScenarioExecutionContext context);
}
