package website.automate.jwebrobot.exceptions;

import website.automate.waml.io.model.action.Action;

import java.text.MessageFormat;

public class WaitTimeTooBigException extends RuntimeException {

    private static final String MESSAGE = "The given time {1} [s] in the action {0} is too big!";

    public WaitTimeTooBigException(Class<? extends Action> actionClass, double time) {
        super(MessageFormat.format(MESSAGE, actionClass.getSimpleName(), time));
    }
}
