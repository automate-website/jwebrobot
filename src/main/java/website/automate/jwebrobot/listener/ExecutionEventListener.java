package website.automate.jwebrobot.listener;

import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.model.Action;

public interface ExecutionEventListener {

    void beforeScenario(ScenarioExecutionContext context);

    void afterScenario(ScenarioExecutionContext context);

    void errorScenario(ScenarioExecutionContext context);

    void beforeAction(ScenarioExecutionContext context, Action action);

    void afterAction(ScenarioExecutionContext context, Action action);

    void errorAction(ScenarioExecutionContext context, Action action);
}
