package website.automate.jwebrobot.exceptions;


public class UnknownActionException extends RuntimeException {
    private static final String MESSAGE = "Unknown action '%s' found.";

    public UnknownActionException(String action) {
        super(String.format(MESSAGE, action));
    }
}
