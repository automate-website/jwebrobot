package website.automate.jwebrobot.models.mapper.actions;

import website.automate.jwebrobot.models.scenario.actions.EnterAction;

public class EnterActionMapper extends ActionMapper<EnterAction> {
    @Override
    public EnterAction map(Object source) {
        EnterAction action = new EnterAction();
        map(source, action);

        return action;
    }

    @Override
    public String getActionName() {
        return EnterAction.ACTION_NAME;
    }
}
