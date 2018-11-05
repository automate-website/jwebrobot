package website.automate.jwebrobot.executor.action;

import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.executor.ActionExecutorUtils;
import website.automate.jwebrobot.executor.ActionResult;
import website.automate.waml.io.model.action.Action;

public interface ActionExecutor<T extends Action> {

    ActionResult execute(T action, ScenarioExecutionContext context, ActionExecutorUtils executionUtils);

    Class<T> getSupportedType();
}
