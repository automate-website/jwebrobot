package website.automate.jwebrobot.expression.action;

import org.springframework.stereotype.Service;
import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.expression.ExpressionEvaluator;
import website.automate.waml.io.model.main.action.ExecuteAction;
import website.automate.waml.io.model.main.criteria.ExecuteCriteria;

@Service
public class ExecuteActionExpressionEvaluator extends ConditionalActionExpressionEvaluator<ExecuteAction> {

    public ExecuteActionExpressionEvaluator(ExpressionEvaluator expressionEvaluator) {
        super(expressionEvaluator);
    }

    @Override
    public void evaluate(ExecuteAction action, ScenarioExecutionContext context) {
        super.evaluate(action, context);
        ExecuteCriteria executeCriteria = action.getExecute();
        executeCriteria.setAsync(evaluateAsString(executeCriteria.getAsync(), context));
    }

    @Override
    public Class<ExecuteAction> getSupportedType() {
        return ExecuteAction.class;
    }
}
