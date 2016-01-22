package website.automate.jwebrobot.exceptions;


import website.automate.jwebrobot.models.scenario.actions.Action;

public class ActionExecutorMissingException extends RuntimeException {
    private static final String MESSAGE = "Missing action executor for action %s.";

    public ActionExecutorMissingException(Class<? extends Action> actionClass) {
        super(String.format(MESSAGE, actionClass.getCanonicalName()));
    }
}
