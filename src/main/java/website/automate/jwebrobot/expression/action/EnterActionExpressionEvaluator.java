package website.automate.jwebrobot.expression.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.expression.ExpressionEvaluator;
import website.automate.waml.io.model.main.action.EnterAction;

@Service
public class EnterActionExpressionEvaluator extends ElementStoreActionExpressionEvaluator<EnterAction> {

    @Autowired
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
