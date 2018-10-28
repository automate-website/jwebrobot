package website.automate.jwebrobot.expression.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.expression.ExpressionEvaluator;
import website.automate.waml.io.model.action.OpenAction;

@Service
public class OpenActionExpressionEvaluator extends ConditionalActionExpressionEvaluator<OpenAction> {

    @Autowired
    public OpenActionExpressionEvaluator(
            ExpressionEvaluator expressionEvaluator) {
        super(expressionEvaluator);
    }

    @Override
    public void evaluate(OpenAction action, ScenarioExecutionContext context) {
        super.evaluate(action, context);
        action.setUrl(evaluate(action.getUrl(), context));
    }

    @Override
    public Class<OpenAction> getSupportedType() {
        return OpenAction.class;
    }
}
