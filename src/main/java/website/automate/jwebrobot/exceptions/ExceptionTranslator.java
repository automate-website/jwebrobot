package website.automate.jwebrobot.exceptions;

import org.openqa.selenium.TimeoutException;

public class ExceptionTranslator {

	public RuntimeException translate(RuntimeException cause){
		RuntimeException translatedException = cause;
		if(cause instanceof TimeoutException){
    		translatedException = new FilterTimeoutException(cause);
    	}
		return translatedException;
	}
}
