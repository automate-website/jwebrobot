package website.automate.jwebrobot.models.mapper.actions;

import website.automate.jwebrobot.models.scenario.actions.SelectAction;

public class SelectActionMapper extends ActionMapper<SelectAction> {
    @Override
    public SelectAction map(Object source) {
        SelectAction action = new SelectAction();
        map(source, action);

        return action;
    }

    @Override
    public String getActionName() {
        return SelectAction.ACTION_NAME;
    }
}
