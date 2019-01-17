package website.automate.jwebrobot.expression.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.expression.ExpressionEvaluator;
import website.automate.waml.io.model.main.action.WaitAction;
import website.automate.waml.io.model.main.criteria.WaitCriteria;

@Service
public class WaitActionExpressionEvaluator extends ConditionalActionExpressionEvaluator<WaitAction> {

    @Autowired
    public WaitActionExpressionEvaluator(
            ExpressionEvaluator expressionEvaluator) {
        super(expressionEvaluator);
    }

    @Override
    public void evaluate(WaitAction action, ScenarioExecutionContext context) {
        super.evaluate(action, context);
        WaitCriteria wait = action.getWait();
        if(wait != null){
            wait.setTime(evaluateAsString(wait.getTime(), context));
        }
    }

    @Override
    public Class<WaitAction> getSupportedType() {
        return WaitAction.class;
    }
}
