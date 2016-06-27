package website.automate.jwebrobot.exceptions;

public class ScenarioExecutionException extends RuntimeException {

    private static final long serialVersionUID = 5235547246946029749L;
    
    public ScenarioExecutionException(String message){
        super(message);
    }
    
    public ScenarioExecutionException(String message, Throwable cause){
    	super(message, cause);
    }
}
