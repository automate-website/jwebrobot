package website.automate.jwebrobot.expression.action;

import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.expression.ExpressionEvaluator;
import website.automate.waml.io.model.action.ConditionalAction;

public abstract class BasicActionExpressionEvaluator<T extends ConditionalAction>
        extends ActionExpressionEvaluator<T> {

    public BasicActionExpressionEvaluator(ExpressionEvaluator expressionEvaluator) {
        super(expressionEvaluator);
    }

    @Override
    public void evaluate(T action, ScenarioExecutionContext context) {
        action.setInvert(evaluate(action.getInvert(), context));
        action.setRegister(evaluate(action.getRegister(), context));
        action.setMeta(evaluate(action.getMeta(), context));
    }
}
