package website.automate.jwebrobot.models.mapper.actions;

import website.automate.jwebrobot.models.scenario.actions.ClickAction;

public class ClickActionMapper extends ActionMapper<ClickAction> {
    private static final String CLICK = "click";

    @Override
    public ClickAction map(Object source) {
        ClickAction action = new ClickAction();
        map(source, action);

        return action;
    }

    @Override
    public String getActionName() {
        return CLICK;
    }
}
