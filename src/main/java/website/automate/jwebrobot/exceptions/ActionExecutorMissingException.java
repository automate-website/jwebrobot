package website.automate.jwebrobot.exceptions;

import website.automate.waml.io.model.main.action.Action;

public class ActionExecutorMissingException extends RuntimeException {

    private static final long serialVersionUID = -1036993601353256378L;

    private static final String MESSAGE = "Missing action executor for action %s.";

    public ActionExecutorMissingException(Class<? extends Action> actionClass) {
        super(String.format(MESSAGE, actionClass.getSimpleName()));
    }
}
