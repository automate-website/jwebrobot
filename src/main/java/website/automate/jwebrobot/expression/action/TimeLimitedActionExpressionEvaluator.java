package website.automate.jwebrobot.expression.action;

import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.expression.ExpressionEvaluator;
import website.automate.waml.io.model.main.action.TimeLimitedAction;

public abstract class TimeLimitedActionExpressionEvaluator<T extends TimeLimitedAction> extends ConditionalActionExpressionEvaluator<T> {

    public TimeLimitedActionExpressionEvaluator(
            ExpressionEvaluator expressionEvaluator) {
        super(expressionEvaluator);
    }

    @Override
    public void evaluate(T action, ScenarioExecutionContext context) {
        super.evaluate(action, context);
        action.setTimeout(evaluate(action.getTimeout(), context));
    }
}
