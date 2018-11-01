package website.automate.jwebrobot.executor.action;

import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.executor.StepExecutionUtils;
import website.automate.waml.io.model.action.Action;

public interface ActionExecutor<T extends Action> {

    void execute(T action, ScenarioExecutionContext context, StepExecutionUtils executionUtils);

    Class<T> getSupportedType();
}
