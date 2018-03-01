package website.automate.jwebrobot.expression.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.expression.ExpressionEvaluator;
import website.automate.waml.io.model.action.WaitAction;
import website.automate.waml.io.model.criteria.WaitCriteria;

@Service
public class WaitActionExpressionEvaluator
        extends ConditionalActionExpressionEvaluator<WaitAction> {

    @Autowired
    public WaitActionExpressionEvaluator(ExpressionEvaluator expressionEvaluator) {
        super(expressionEvaluator);
    }

    @Override
    public void evaluate(WaitAction action, ScenarioExecutionContext context) {
        super.evaluate(action, context);
        WaitCriteria criteria = action.getWait();

        criteria.setTime(evaluate(criteria.getTime(), context));
    }

    @Override
    public Class<WaitAction> getSupportedType() {
        return WaitAction.class;
    }
}
