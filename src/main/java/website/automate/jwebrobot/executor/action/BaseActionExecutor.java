package website.automate.jwebrobot.executor.action;

import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.exceptions.ExceptionTranslator;
import website.automate.jwebrobot.listener.ExecutionEventListeners;
import website.automate.waml.io.model.action.Action;
import website.automate.waml.io.model.action.TimeLimitedAction;

public abstract class BaseActionExecutor<T extends Action> implements ActionExecutor<T> {

	static final long DEFAULT_TIMEOUT_S = 1;
	
    private ExecutionEventListeners listener;
    
    private ExceptionTranslator exceptionTranslator;
    
    public BaseActionExecutor(ExecutionEventListeners listener,
    		ExceptionTranslator exceptionTranslator) {
        this.listener = listener;
        this.exceptionTranslator = exceptionTranslator;
    }
    
    @Override
    public void execute(T action, ScenarioExecutionContext context){
        listener.beforeAction(context, action);
        
        T resultAction = action;
        try {
            if(preHandle(action, context)){
                context.countStep(action);
                perform(action, context);
            }
        } catch (RuntimeException e) {
        	RuntimeException translatedException = exceptionTranslator.translate(e);
            listener.errorAction(context, action, translatedException);
            throw translatedException;
        }
        
        listener.afterAction(context, resultAction);
    }
    
    public boolean preHandle(T action, ScenarioExecutionContext context){
        return true;
    }
    
    public abstract void perform(T action, ScenarioExecutionContext context);
    
    protected Long getActionTimeout(TimeLimitedAction action, ScenarioExecutionContext context){
        Long globalContextTimeout = context.getGlobalContext().getOptions().getTimeout();
        if(globalContextTimeout != null){
            return globalContextTimeout;
        }
        
        String actionTimeout = action.getTimeout();
        if(actionTimeout != null){
            return Long.parseLong(actionTimeout);
        }
        
        String scenarioTimeout = context.getScenario().getTimeout();
        if(scenarioTimeout != null){
        	return Long.parseLong(scenarioTimeout);
        }
        
        return DEFAULT_TIMEOUT_S;
    }
}
