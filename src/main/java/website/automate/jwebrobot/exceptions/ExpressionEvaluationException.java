package website.automate.jwebrobot.exceptions;

import java.text.MessageFormat;

public class ExpressionEvaluationException extends RuntimeException {

    private static final long serialVersionUID = -8420061435007979181L;
    
    public ExpressionEvaluationException(String expression){
        super(MessageFormat.format("Can not evaluation expression {0}.", 
                expression));
    }

}
