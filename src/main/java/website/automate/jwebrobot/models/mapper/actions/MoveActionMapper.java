package website.automate.jwebrobot.models.mapper.actions;

import website.automate.jwebrobot.models.scenario.actions.MoveAction;

public class MoveActionMapper extends ActionMapper<MoveAction> {
    @Override
    public MoveAction map(Object source) {
        MoveAction action = new MoveAction();
        map(source, action);

        return action;
    }

    @Override
    public String getActionName() {
        return MoveAction.ACTION_NAME;
    }
}
