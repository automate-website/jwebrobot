package website.automate.jwebrobot.executor.action;

import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.listener.ExecutionEventListeners;
import website.automate.waml.io.model.CriterionValue;
import website.automate.waml.io.model.action.Action;
import website.automate.waml.io.model.action.TimeLimitedAction;

public abstract class BaseActionExecutor<T extends Action> implements ActionExecutor<T> {

    private ExecutionEventListeners listener;
    
    public BaseActionExecutor(ExecutionEventListeners listener) {
        this.listener = listener;
    }
    
    @Override
    public void execute(T action, ScenarioExecutionContext context){
        listener.beforeAction(context, action);
        
        try {
            if(preHandle(action, context)){
                T preprocessedAction = preprocess(action, context);
                perform(preprocessedAction, context);
            }
        } catch (Exception e) {
            listener.errorAction(context, action, e);
            throw e;
        }
        
        listener.afterAction(context, action);
    }
    
    public boolean preHandle(T action, ScenarioExecutionContext context){
        return true;
    }
    
    public T preprocess(T action, ScenarioExecutionContext context){
        return action;
    }

    public abstract void perform(T action, ScenarioExecutionContext context);
    
    protected Long getActionTimeout(TimeLimitedAction action, ScenarioExecutionContext context){
        Long globalContextTimeout = context.getGlobalContext().getOptions().getTimeout();
        if(globalContextTimeout != null){
            return globalContextTimeout;
        }
        
        CriterionValue actionTimeout = action.getTimeout();
        if(actionTimeout != null){
            return actionTimeout.toLong();
        }
        
        return new CriterionValue(context.getScenario().getTimeout()).toLong();
    } 
}
