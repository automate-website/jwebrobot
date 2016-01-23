package website.automate.jwebrobot.models.mapper.actions;

import website.automate.jwebrobot.models.scenario.actions.IncludeAction;

public class IncludeActionMapper extends ActionMapper<IncludeAction> {
    @Override
    public IncludeAction map(Object source) {
        IncludeAction action = new IncludeAction();
        map(source, action);

        return action;
    }

    @Override
    public String getActionName() {
        return IncludeAction.ACTION_NAME;
    }
}
