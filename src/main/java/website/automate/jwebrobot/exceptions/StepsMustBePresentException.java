package website.automate.jwebrobot.exceptions;

public class StepsMustBePresentException extends RuntimeException {
    private static final String MESSAGE = "Steps must be provided in scenario %s.";

    public StepsMustBePresentException(String scenarioName) {
        super(String.format(MESSAGE, scenarioName));
    }
}
