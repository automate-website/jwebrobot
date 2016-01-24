package website.automate.jwebrobot.executor.action;

import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.models.scenario.actions.Action;

public interface ActionExecutor<T extends Action> {

    void execute(Action action, ScenarioExecutionContext context);

    Class<T> getActionType();
}
