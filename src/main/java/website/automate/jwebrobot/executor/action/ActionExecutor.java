package website.automate.jwebrobot.executor.action;

import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.model.Action;
import website.automate.jwebrobot.model.ActionType;

public interface ActionExecutor {

    void execute(Action action, ScenarioExecutionContext context);

    ActionType getActionType();
}
