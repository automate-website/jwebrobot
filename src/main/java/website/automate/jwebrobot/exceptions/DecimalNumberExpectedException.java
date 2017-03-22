package website.automate.jwebrobot.exceptions;

import website.automate.waml.io.model.action.Action;

public class DecimalNumberExpectedException extends RuntimeException {

    private static final long serialVersionUID = -2089631406548361875L;

    private static final String MESSAGE = "Decimal number is expected in %s but \"%s\" was given!";

    public DecimalNumberExpectedException(Class<? extends Action> actionClass, String str) {
        super(String.format(MESSAGE, actionClass.getSimpleName(), str));
    }
}
