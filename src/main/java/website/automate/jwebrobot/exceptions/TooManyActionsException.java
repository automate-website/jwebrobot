package website.automate.jwebrobot.exceptions;


public class TooManyActionsException extends RuntimeException {
    
    private static final long serialVersionUID = 773457572511778934L;
    
    private static final String MESSAGE = "A step must contain exactly one action, %d found instead.";

    public TooManyActionsException(int actionCount) {
        super(String.format(MESSAGE, actionCount));
    }
}
