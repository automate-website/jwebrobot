package website.automate.jwebrobot.expression.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import website.automate.jwebrobot.expression.ExpressionEvaluator;
import website.automate.waml.io.model.action.MoveAction;

@Service
public class MoveActionExpressionEvaluator extends FilterActionExpressionEvaluator<MoveAction> {

    @Autowired
    public MoveActionExpressionEvaluator(
            ExpressionEvaluator expressionEvaluator) {
        super(expressionEvaluator);
    }

    @Override
    public Class<MoveAction> getSupportedType() {
        return MoveAction.class;
    }

}
