package website.automate.jwebrobot.executor.action;

import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.models.scenario.actions.WaitAction;

public class WaitActionExecutor extends BaseActionExecutor<WaitAction> {

    @Override
    public Class<WaitAction> getActionType() {
        return WaitAction.class;
    }

    @Override
    public void safeExecute(final WaitAction action, ScenarioExecutionContext context) {
        long time = Long.parseLong(action.getTime().getValue());
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {

        }

    }

}
