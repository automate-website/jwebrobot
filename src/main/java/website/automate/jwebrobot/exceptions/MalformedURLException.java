package website.automate.jwebrobot.exceptions;

import static java.text.MessageFormat.format;

public class MalformedURLException extends RuntimeException {

	private static final long serialVersionUID = 8515336825753369430L;

	private String url;
	
	public MalformedURLException(String url, Throwable cause){
		super(format("Failed parsing open action url {0}", url), cause);
		this.url = url;
	}
	
	public String getUrl() {
		return url;
	}
}
