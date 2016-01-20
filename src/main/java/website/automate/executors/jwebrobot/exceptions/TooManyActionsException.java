package website.automate.executors.jwebrobot.exceptions;


public class TooManyActionsException extends RuntimeException {
    private static final String MESSAGE = "A step must contain exactly one action, %d found instead.";

    public TooManyActionsException(int actiponCount) {
        super(String.format(MESSAGE, actiponCount));
    }
}
