package website.automate.jwebrobot.expression.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import website.automate.jwebrobot.expression.ExpressionEvaluator;
import website.automate.waml.io.model.action.SelectAction;

@Service
public class SelectActionExpressionEvaluator extends ElementStoreActionExpressionEvaluator<SelectAction> {

    @Autowired
    public SelectActionExpressionEvaluator(
            ExpressionEvaluator expressionEvaluator) {
        super(expressionEvaluator);
    }

    @Override
    public Class<SelectAction> getSupportedType() {
        return SelectAction.class;
    }

}
