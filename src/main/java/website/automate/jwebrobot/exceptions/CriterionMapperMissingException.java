package website.automate.jwebrobot.exceptions;


public class CriterionMapperMissingException extends RuntimeException {
    private static final String MESSAGE = "Missing criterion mapper for criterion %s.";

    public CriterionMapperMissingException(String criterionName) {
        super(String.format(MESSAGE, criterionName));
    }
}
