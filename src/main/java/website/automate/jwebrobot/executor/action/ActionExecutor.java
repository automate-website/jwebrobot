package website.automate.jwebrobot.executor.action;

import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.waml.io.model.action.Action;

public interface ActionExecutor<T extends Action> {

    void execute(T action, ScenarioExecutionContext context);

    Class<T> getSupportedType();
}
