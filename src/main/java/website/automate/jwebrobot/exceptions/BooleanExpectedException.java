package website.automate.jwebrobot.exceptions;

import website.automate.waml.io.model.action.Action;

public class BooleanExpectedException extends RuntimeException {

    private static final long serialVersionUID = -7878187212121920894L;

    private static final String MESSAGE = "Boolean is expected in %s but \"%s\" was given!";
    private static final String MESSAGE_SHORT = "Boolean is expected but \"%s\" was given!";

    public BooleanExpectedException(Class<? extends Action> actionClass, String str) {
        super(String.format(MESSAGE, actionClass.getSimpleName(), str));
    }

    public BooleanExpectedException(String str) {
        super(String.format(MESSAGE_SHORT, str));
    }
}
