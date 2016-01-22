package website.automate.jwebrobot.exceptions;

public class UnknownMetadataException extends RuntimeException {

    private static final String MESSAGE = "Unknown metadata '%s' found.";

    public UnknownMetadataException(String metadata) {
        super(String.format(MESSAGE, metadata));
    }
}
