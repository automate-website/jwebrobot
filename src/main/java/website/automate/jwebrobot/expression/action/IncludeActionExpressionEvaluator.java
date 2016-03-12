package website.automate.jwebrobot.expression.action;

import com.google.inject.Inject;

import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.expression.ExpressionEvaluator;
import website.automate.waml.io.model.action.IncludeAction;

public class IncludeActionExpressionEvaluator extends ConditionalActionExpressionEvaluator<IncludeAction> {

    @Inject
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
