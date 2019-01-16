package website.automate.jwebrobot.expression.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.expression.ExpressionEvaluator;
import website.automate.waml.io.model.main.action.ExportAction;

import java.util.Map;

@Service
public class ExportActionExpressionEvaluator extends ConditionalActionExpressionEvaluator<ExportAction> {

    @Autowired
    public ExportActionExpressionEvaluator(
            ExpressionEvaluator expressionEvaluator) {
        super(expressionEvaluator);
    }

    @Override
    public void evaluate(ExportAction action, ScenarioExecutionContext context) {
        super.evaluate(action, context);

        Map<String, Object> facts = action.getExport().getFacts();
        facts.keySet()
            .stream()
            .map(key -> evaluateAsString(key, context));
        facts.values()
            .stream()
            .forEach(value -> evaluateIfExpression(value, context));
    }

    private Object evaluateIfExpression(Object value, ScenarioExecutionContext context){
        if(value instanceof String){
            String valueStr = String.class.cast(value);
            return evaluateAsObject(valueStr, context);
        }
        return value;
    }

    @Override
    public Class<ExportAction> getSupportedType() {
        return ExportAction.class;
    }
}
