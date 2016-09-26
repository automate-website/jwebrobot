package website.automate.jwebrobot.expression.action;

import com.google.inject.Inject;

import website.automate.jwebrobot.expression.ExpressionEvaluator;
import website.automate.waml.io.model.action.EnsureAction;

public class EnsureActionExpressionEvaluator extends ElementStoreActionExpressionEvaluator<EnsureAction> {

    @Inject
    public EnsureActionExpressionEvaluator(
            ExpressionEvaluator expressionEvaluator) {
        super(expressionEvaluator);
    }

    @Override
    public Class<EnsureAction> getSupportedType() {
        return EnsureAction.class;
    }
}
