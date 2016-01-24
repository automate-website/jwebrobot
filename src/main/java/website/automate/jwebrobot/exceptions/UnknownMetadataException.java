package website.automate.jwebrobot.exceptions;

public class UnknownMetadataException extends RuntimeException {

    private static final long serialVersionUID = -7013522149513637427L;

    private static final String MESSAGE = "Unknown metadata '%s' found.";

    public UnknownMetadataException(String metadata) {
        super(String.format(MESSAGE, metadata));
    }
}
