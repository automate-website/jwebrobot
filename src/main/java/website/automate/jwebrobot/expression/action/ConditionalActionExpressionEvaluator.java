package website.automate.jwebrobot.expression.action;

import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.expression.ExpressionEvaluator;
import website.automate.waml.io.model.action.ConditionalAction;

public abstract class ConditionalActionExpressionEvaluator<T extends ConditionalAction>
        extends BasicActionExpressionEvaluator<T> {

    public ConditionalActionExpressionEvaluator(ExpressionEvaluator expressionEvaluator) {
        super(expressionEvaluator);
    }

    @Override
    public void evaluate(T action, ScenarioExecutionContext context) {
        super.evaluate(action, context);

        action.setWhen(evaluate(action.getWhen(), context));
        action.setUnless(evaluate(action.getUnless(), context));
    }
}
