package website.automate.jwebrobot.exceptions;

public class FilterTimeoutException extends RuntimeException {

	private static final long serialVersionUID = 3479228038349781644L;

	public FilterTimeoutException(Throwable cause){
		super("Timed out waiting for element matching given criteria", cause);
	}
}
