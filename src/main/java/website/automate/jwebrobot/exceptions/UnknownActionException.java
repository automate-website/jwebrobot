package website.automate.jwebrobot.exceptions;


public class UnknownActionException extends RuntimeException {
    
    private static final long serialVersionUID = 2951039968520535621L;
    
    private static final String MESSAGE = "Unknown action '%s' found.";

    public UnknownActionException(String action) {
        super(String.format(MESSAGE, action));
    }
}
