package website.automate.jwebrobot.exceptions;

import website.automate.jwebrobot.model.ActionType;

public class ActionExecutorMissingException extends RuntimeException {

    private static final long serialVersionUID = -1036993601353256378L;

    private static final String MESSAGE = "Missing action executor for action %s.";

    public ActionExecutorMissingException(ActionType actionType) {
        super(String.format(MESSAGE, actionType.getName()));
    }
}
