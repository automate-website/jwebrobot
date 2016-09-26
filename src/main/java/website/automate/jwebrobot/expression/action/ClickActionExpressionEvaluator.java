package website.automate.jwebrobot.expression.action;

import com.google.inject.Inject;

import website.automate.jwebrobot.expression.ExpressionEvaluator;
import website.automate.waml.io.model.action.ClickAction;

public class ClickActionExpressionEvaluator extends ElementStoreActionExpressionEvaluator<ClickAction> {

    @Inject
    public ClickActionExpressionEvaluator(
            ExpressionEvaluator expressionEvaluator) {
        super(expressionEvaluator);
    }

    @Override
    public Class<ClickAction> getSupportedType() {
        return ClickAction.class;
    }

}
