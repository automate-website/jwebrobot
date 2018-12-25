package website.automate.jwebrobot.executor.action;

import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.executor.ActionExecutorUtils;
import website.automate.jwebrobot.executor.ActionExecutionResult;
import website.automate.waml.io.model.main.action.Action;

public interface ActionExecutor<T extends Action> {

    ActionExecutionResult execute(T action, ScenarioExecutionContext context, ActionExecutorUtils executionUtils);

    Class<T> getSupportedType();
}
