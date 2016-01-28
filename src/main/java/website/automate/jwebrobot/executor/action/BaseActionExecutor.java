package website.automate.jwebrobot.executor.action;

import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.model.Action;

public abstract class BaseActionExecutor implements ActionExecutor {

    @Override
    public void execute(Action action, ScenarioExecutionContext context){
        if(preHandle(action, context)){
            Action preprocessedAction = preprocess(action, context);
            perform(preprocessedAction, context);
        }
    }
    
    public boolean preHandle(Action action, ScenarioExecutionContext context){
        return true;
    }
    
    public Action preprocess(Action action, ScenarioExecutionContext context){
        return action;
    }

    public abstract void perform(Action action, ScenarioExecutionContext context);
}
