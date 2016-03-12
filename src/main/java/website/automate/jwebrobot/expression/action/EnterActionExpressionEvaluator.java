package website.automate.jwebrobot.expression.action;

import com.google.inject.Inject;

import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.expression.ExpressionEvaluator;
import website.automate.waml.io.model.action.EnterAction;

public class EnterActionExpressionEvaluator extends FilterActionExpressionEvaluator<EnterAction> {

    @Inject
    public EnterActionExpressionEvaluator(
            ExpressionEvaluator expressionEvaluator) {
        super(expressionEvaluator);
    }

    @Override
    public void evaluate(EnterAction action, ScenarioExecutionContext context) {
        super.evaluate(action, context);
        action.setInput(evaluate(action.getInput(), context));
    }

    @Override
    public Class<EnterAction> getSupportedType() {
        return EnterAction.class;
    }
}
