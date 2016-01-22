package website.automate.jwebrobot.models.mapper.actions;

import website.automate.jwebrobot.models.scenario.actions.OpenAction;

public class OpenActionMapper extends ActionMapper<OpenAction> {
    @Override
    public OpenAction map(Object source) {
        OpenAction action = new OpenAction();
        map(source, action);

        return action;
    }

    @Override
    public String getActionName() {
        return OpenAction.ACTION_NAME;
    }
}
