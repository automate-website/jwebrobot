package website.automate.jwebrobot.expression;

import java.text.MessageFormat;

import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.model.ActionType;

public class ExpressionEvaluationException extends RuntimeException {

    private static final long serialVersionUID = -8420061435007979181L;
    
    public ExpressionEvaluationException(String expression, ActionType actionType, String criteriaName, ScenarioExecutionContext context){
        super(MessageFormat.format("Can not evaluation expression {0} at scenario: {1}, action: {2}, criteria: {3}", 
                expression, context.getScenario().getName(), actionType.getName(), criteriaName));
    }

}
