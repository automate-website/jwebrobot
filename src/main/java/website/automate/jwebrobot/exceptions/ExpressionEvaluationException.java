package website.automate.jwebrobot.exceptions;

import java.text.MessageFormat;

public class ExpressionEvaluationException extends RuntimeException {

    private static final long serialVersionUID = -8420061435007979181L;
    
    private String expression;
    
    public ExpressionEvaluationException(String expression){
        super(MessageFormat.format("Can not evaluate expression {0}.", 
                expression));
        this.expression = expression;
    }

	public String getExpression() {
		return expression;
	}
}
