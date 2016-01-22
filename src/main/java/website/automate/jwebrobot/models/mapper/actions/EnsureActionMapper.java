package website.automate.jwebrobot.models.mapper.actions;

import website.automate.jwebrobot.models.scenario.actions.EnsureAction;

public class EnsureActionMapper extends ActionMapper<EnsureAction> {
    @Override
    public EnsureAction map(Object source) {
        EnsureAction action = new EnsureAction();
        map(source, action);

        return action;
    }

    @Override
    public String getActionName() {
        return EnsureAction.ACTION_NAME;
    }
}
