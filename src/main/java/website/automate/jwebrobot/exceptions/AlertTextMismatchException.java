package website.automate.jwebrobot.exceptions;

import website.automate.waml.io.model.main.action.Action;

public class AlertTextMismatchException extends RuntimeException {

    private static final long serialVersionUID = -8978125317340207924L;

    private static final String MESSAGE = "\"%s\" was expected in %s but \"%s\" was given!";

    public AlertTextMismatchException(Class<? extends Action> actionClass, String expectedAlertText, String actualAlertText) {
        super(String.format(MESSAGE, actionClass.getSimpleName(), expectedAlertText, actualAlertText));
    }
}
