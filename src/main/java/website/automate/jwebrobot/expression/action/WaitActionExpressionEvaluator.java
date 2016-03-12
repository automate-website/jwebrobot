package website.automate.jwebrobot.expression.action;

import com.google.inject.Inject;

import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.expression.ExpressionEvaluator;
import website.automate.waml.io.model.action.WaitAction;

public class WaitActionExpressionEvaluator extends ConditionalActionExpressionEvaluator<WaitAction> {

    @Inject
    public WaitActionExpressionEvaluator(
            ExpressionEvaluator expressionEvaluator) {
        super(expressionEvaluator);
    }

    @Override
    public void evaluate(WaitAction action, ScenarioExecutionContext context) {
        super.evaluate(action, context);
        action.setTime(evaluate(action.getTime(), context));
    }

    @Override
    public Class<WaitAction> getSupportedType() {
        return WaitAction.class;
    }
}
