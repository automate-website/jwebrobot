package website.automate.jwebrobot.listener;

import website.automate.jwebrobot.context.GlobalExecutionContext;
import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.model.Action;

public interface ExecutionEventListener {

    void beforeExecution(GlobalExecutionContext context);
    
    void afterExecution(GlobalExecutionContext context);
    
    void errorExecution(GlobalExecutionContext context, Exception exception);
    
    void beforeScenario(ScenarioExecutionContext context);

    void afterScenario(ScenarioExecutionContext context);

    void errorScenario(ScenarioExecutionContext context, Exception exception);

    void beforeAction(ScenarioExecutionContext context, Action action);

    void afterAction(ScenarioExecutionContext context, Action action);

    void errorAction(ScenarioExecutionContext context, Action action, Exception exception);
}
