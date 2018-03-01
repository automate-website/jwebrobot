package website.automate.jwebrobot.expression.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.expression.ExpressionEvaluator;
import website.automate.waml.io.model.action.EnterAction;
import website.automate.waml.io.model.criteria.EnterCriteria;

@Service
public class EnterActionExpressionEvaluator extends FilterActionExpressionEvaluator<EnterAction> {

    @Autowired
    public EnterActionExpressionEvaluator(ExpressionEvaluator expressionEvaluator) {
        super(expressionEvaluator);
    }

    @Override
    public void evaluate(EnterAction action, ScenarioExecutionContext context) {
        super.evaluate(action, context);
        EnterCriteria criteria = action.getEnter();

        criteria.setInput(evaluate(criteria.getInput(), context));
    }

    @Override
    public Class<EnterAction> getSupportedType() {
        return EnterAction.class;
    }
}
