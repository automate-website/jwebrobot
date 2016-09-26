package website.automate.jwebrobot.expression.action;

import com.google.inject.Inject;

import website.automate.jwebrobot.expression.ExpressionEvaluator;
import website.automate.waml.io.model.action.SelectAction;

public class SelectActionExpressionEvaluator extends ElementStoreActionExpressionEvaluator<SelectAction> {

    @Inject
    public SelectActionExpressionEvaluator(
            ExpressionEvaluator expressionEvaluator) {
        super(expressionEvaluator);
    }

    @Override
    public Class<SelectAction> getSupportedType() {
        return SelectAction.class;
    }

}
