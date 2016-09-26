package website.automate.jwebrobot.expression.action;

import com.google.inject.Inject;

import website.automate.jwebrobot.expression.ExpressionEvaluator;
import website.automate.waml.io.model.action.MoveAction;

public class MoveActionExpressionEvaluator extends ElementStoreActionExpressionEvaluator<MoveAction> {

    @Inject
    public MoveActionExpressionEvaluator(
            ExpressionEvaluator expressionEvaluator) {
        super(expressionEvaluator);
    }

    @Override
    public Class<MoveAction> getSupportedType() {
        return MoveAction.class;
    }

}
