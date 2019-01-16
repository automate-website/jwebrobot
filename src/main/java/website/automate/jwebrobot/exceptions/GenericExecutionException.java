package website.automate.jwebrobot.exceptions;

public class GenericExecutionException extends RuntimeException {

    private static final long serialVersionUID = 5479228038349781644L;

    public GenericExecutionException(String msg, Throwable e){
        super(msg, e);
    }
}
