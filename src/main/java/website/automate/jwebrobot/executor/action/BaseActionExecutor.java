package website.automate.jwebrobot.executor.action;

import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.listener.ExecutionEventListeners;
import website.automate.jwebrobot.model.Action;
import website.automate.jwebrobot.model.CriteriaType;
import website.automate.jwebrobot.model.CriteriaValue;

public abstract class BaseActionExecutor implements ActionExecutor {

    private ExecutionEventListeners listener;
    
    public BaseActionExecutor(ExecutionEventListeners listener) {
        this.listener = listener;
    }
    
    @Override
    public void execute(Action action, ScenarioExecutionContext context){
        listener.beforeAction(context, action);
        
        try {
            if(preHandle(action, context)){
                Action preprocessedAction = preprocess(action, context);
                perform(preprocessedAction, context);
            }
        } catch (Exception e) {
            listener.errorAction(context, action, e);
            throw e;
        }
        
        listener.afterAction(context, action);
    }
    
    public boolean preHandle(Action action, ScenarioExecutionContext context){
        return true;
    }
    
    public Action preprocess(Action action, ScenarioExecutionContext context){
        return action;
    }

    public abstract void perform(Action action, ScenarioExecutionContext context);
    
    protected Long getActionTimeout(Action action, ScenarioExecutionContext context){
        Long globalContextTimeout = context.getGlobalContext().getOptions().getTimeout();
        if(globalContextTimeout != null){
            return globalContextTimeout;
        }
        
        CriteriaValue actionTimeout = action.getCriteria(CriteriaType.TIMEOUT);
        if(actionTimeout != null){
            return actionTimeout.asLong();
        }
        
        return new CriteriaValue(context.getScenario().getTimeout()).asLong();
    }
}
