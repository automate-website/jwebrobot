package website.automate.jwebrobot.exceptions;

import static java.text.MessageFormat.format;

public class MalformedURLException extends ScenarioExecutionException {

	private static final long serialVersionUID = 8515336825753369430L;

	public MalformedURLException(String malformedUrl, Throwable cause){
		super(format("Failed parsing open action url {0}", malformedUrl), cause);
	}
}
