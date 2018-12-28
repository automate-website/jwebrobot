package website.automate.jwebrobot.expression.action;

import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.expression.ExpressionEvaluator;
import website.automate.waml.io.model.main.action.ConditionalAction;

public abstract class ConditionalActionExpressionEvaluator<T extends ConditionalAction> extends ActionExpressionEvaluator<T> {

    public ConditionalActionExpressionEvaluator(ExpressionEvaluator expressionEvaluator) {
        super(expressionEvaluator);
    }
    
    @Override
    public void evaluateTemplateAsString(T action, ScenarioExecutionContext context) {
        action.setWhen(evaluateTemplateAsString(action.getWhen(), context));
        action.setUnless(evaluateTemplateAsString(action.getUnless(), context));
    }
}
