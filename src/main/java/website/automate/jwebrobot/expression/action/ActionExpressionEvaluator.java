package website.automate.jwebrobot.expression.action;

import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.expression.ExpressionEvaluator;
import website.automate.waml.io.model.CriterionValue;
import website.automate.waml.io.model.action.Action;

public abstract class ActionExpressionEvaluator<T extends Action> {

    protected ExpressionEvaluator expressionEvaluator;
    
    public ActionExpressionEvaluator(ExpressionEvaluator expressionEvaluator){
        this.expressionEvaluator = expressionEvaluator;
    }
    
    abstract public void evaluate(T action, ScenarioExecutionContext context);
    
    abstract public Class<T> getSupportedType();
    
    protected String evaluate(String criterionValue, ScenarioExecutionContext context){
        if(criterionValue == null){
            return null;
        }
        Object evaluatedExpression = expressionEvaluator.evaluate(criterionValue, context.getMemory());
        return evaluatedExpression.toString();
    }
    
    protected CriterionValue evaluate(CriterionValue criterionValue, ScenarioExecutionContext context){
        if(criterionValue == null){
            return null;
        }
        Object evaluatedExpression = expressionEvaluator.evaluate(criterionValue.getValue().toString(), context.getMemory());
        return new CriterionValue(evaluatedExpression);
    }
}
