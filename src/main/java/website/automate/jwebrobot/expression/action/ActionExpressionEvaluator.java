package website.automate.jwebrobot.expression.action;

import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.expression.ExpressionEvaluator;
import website.automate.waml.io.model.action.Action;

public abstract class ActionExpressionEvaluator<T extends Action> {

    protected ExpressionEvaluator expressionEvaluator;
    
    public ActionExpressionEvaluator(ExpressionEvaluator expressionEvaluator){
        this.expressionEvaluator = expressionEvaluator;
    }
    
    abstract public void evaluate(T action, ScenarioExecutionContext context);
    
    abstract public Class<T> getSupportedType();
    
    protected String evaluate(String value, ScenarioExecutionContext context){
        if(value == null){
            return null;
        }
        return expressionEvaluator.evaluate(value, context.getTotalMemory());
    }
}
