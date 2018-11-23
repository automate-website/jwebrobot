package website.automate.jwebrobot.expression.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.expression.ExpressionEvaluator;
import website.automate.waml.io.model.main.action.IncludeAction;

@Service
public class IncludeActionExpressionEvaluator extends ConditionalActionExpressionEvaluator<IncludeAction> {

    @Autowired
    public IncludeActionExpressionEvaluator(
            ExpressionEvaluator expressionEvaluator) {
        super(expressionEvaluator);
    }

    @Override
    public void evaluate(IncludeAction action, ScenarioExecutionContext context) {
        super.evaluate(action, context);
        action.setScenario(evaluate(action.getScenario(), context));
    }

    @Override
    public Class<IncludeAction> getSupportedType() {
        return IncludeAction.class;
    }
}
