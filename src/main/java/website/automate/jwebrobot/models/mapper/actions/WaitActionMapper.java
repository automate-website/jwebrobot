package website.automate.jwebrobot.models.mapper.actions;

import website.automate.jwebrobot.models.scenario.actions.WaitAction;

public class WaitActionMapper extends ActionMapper<WaitAction> {
    @Override
    public WaitAction map(Object source) {
        WaitAction action = new WaitAction();
        map(source, action);

        return action;
    }

    @Override
    public String getActionName() {
        return WaitAction.ACTION_NAME;
    }
}
