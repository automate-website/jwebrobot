package website.automate.jwebrobot.expression.action;

import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.expression.ExpressionEvaluator;
import website.automate.waml.io.model.action.ElementStoreAction;

public abstract class ElementStoreActionExpressionEvaluator<T extends ElementStoreAction> extends FilterActionExpressionEvaluator<T> {

    public ElementStoreActionExpressionEvaluator(
            ExpressionEvaluator expressionEvaluator) {
        super(expressionEvaluator);
    }

    @Override
    public void evaluate(T action, ScenarioExecutionContext context) {
        super.evaluate(action, context);

        action.setStore(evaluate(action.getStore(), context));
    }
}
