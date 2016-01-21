package website.automate.jwebrobot.executor;

import static java.text.MessageFormat.format;
import website.automate.jwebrobot.exceptions.UnsupportedActionException;
import website.automate.jwebrobot.models.scenario.actions.Action;

public abstract class BaseActionExecutor<T extends Action> implements ActionExecutor<T> {

    @SuppressWarnings("unchecked")
    @Override
    public void execute(Action action, ActionExecutionContext context){
        Class<? extends Action> actualActionType = action.getClass();
        Class<? extends Action> actionType = getActionType();
        if(actualActionType != getActionType()){
            throw new UnsupportedActionException(format("Action type {0} is not supported by executor {1}.", actualActionType, actionType));
        }
        safeExecute((T)action, context);
    }
    
    public abstract void safeExecute(T action, ActionExecutionContext context);
}
