package website.automate.jwebrobot.exceptions;

import static java.text.MessageFormat.format;

public class ExpressionEvaluationException extends ScenarioExecutionException {

    private static final long serialVersionUID = -8420061435007979181L;
    
    public ExpressionEvaluationException(String expression, Throwable e){
        super(format("Can not evaluation expression {0}.", 
                expression), e);
    }

}
