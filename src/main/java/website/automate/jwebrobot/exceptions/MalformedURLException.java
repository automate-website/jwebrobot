package website.automate.jwebrobot.exceptions;

public class MalformedURLException extends RuntimeException {

	private static final long serialVersionUID = 8515336825753369430L;

	public MalformedURLException(String message, Throwable cause){
		super(message, cause);
	}
}
