package website.automate.jwebrobot.exceptions;


public class UnknownCriterionException extends RuntimeException {
    
    private static final long serialVersionUID = 6928164754150142750L;
    
    private static final String MESSAGE = "Unknown criterion '%s' found.";

    public UnknownCriterionException(String criterion) {
        super(String.format(MESSAGE, criterion));
    }
}
