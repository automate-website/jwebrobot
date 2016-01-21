package website.automate.jwebrobot.executor;

import website.automate.jwebrobot.models.scenario.actions.Action;

public interface ActionExecutor<T extends Action> {

    void execute(Action action, ActionExecutionContext context);
    
    Class<T> getActionType();
}
