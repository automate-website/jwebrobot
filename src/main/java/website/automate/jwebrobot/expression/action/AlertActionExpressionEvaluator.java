package website.automate.jwebrobot.expression.action;

import com.google.inject.Inject;
import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.expression.ExpressionEvaluator;
import website.automate.waml.io.model.action.AlertAction;

public class AlertActionExpressionEvaluator extends ConditionalActionExpressionEvaluator<AlertAction> {

    @Inject
    public AlertActionExpressionEvaluator(
        ExpressionEvaluator expressionEvaluator) {
        super(expressionEvaluator);
    }

    @Override
    public void evaluate(AlertAction action, ScenarioExecutionContext context) {
        super.evaluate(action, context);
        action.setConfirm(evaluate(action.getConfirm(), context));
        action.setText(evaluate(action.getText(), context));
        action.setInput(evaluate(action.getInput(), context));
    }

    @Override
    public Class<AlertAction> getSupportedType() {
        return AlertAction.class;
    }

}





