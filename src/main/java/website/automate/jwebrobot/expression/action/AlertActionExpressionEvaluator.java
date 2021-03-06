package website.automate.jwebrobot.expression.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.expression.ExpressionEvaluator;
import website.automate.waml.io.model.main.action.AlertAction;
import website.automate.waml.io.model.main.criteria.AlertCriteria;

@Service
public class AlertActionExpressionEvaluator extends ConditionalActionExpressionEvaluator<AlertAction> {

    @Autowired
    public AlertActionExpressionEvaluator(
        ExpressionEvaluator expressionEvaluator) {
        super(expressionEvaluator);
    }

    @Override
    public void evaluate(AlertAction action, ScenarioExecutionContext context) {
        super.evaluate(action, context);

        AlertCriteria alert = action.getAlert();
        alert.setConfirm(evaluateAsString(alert.getConfirm(), context));
        alert.setText(evaluateAsString(alert.getText(), context));
        alert.setInput(evaluateAsString(alert.getInput(), context));
    }

    @Override
    public Class<AlertAction> getSupportedType() {
        return AlertAction.class;
    }

}





