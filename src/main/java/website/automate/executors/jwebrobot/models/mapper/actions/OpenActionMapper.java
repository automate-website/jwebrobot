package website.automate.executors.jwebrobot.models.mapper.actions;

import website.automate.executors.jwebrobot.models.mapper.actions.ActionMapper;
import website.automate.executors.jwebrobot.models.scenario.actions.OpenAction;

public class OpenActionMapper extends ActionMapper<OpenAction> {
    private static final String CLICK = "open";

    @Override
    public OpenAction map(Object source) {
        OpenAction action = new OpenAction();
        map(source, action);

        return action;
    }


    @Override
    public String getActionName() {
        return CLICK;
    }
}
