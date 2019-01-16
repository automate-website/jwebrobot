package website.automate.jwebrobot.expression.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.expression.ExpressionEvaluator;
import website.automate.waml.io.model.main.action.DefineAction;

import java.util.Map;

@Service
public class DefineActionExpressionEvaluator extends ConditionalActionExpressionEvaluator<DefineAction> {

    @Autowired
    public DefineActionExpressionEvaluator(
            ExpressionEvaluator expressionEvaluator) {
        super(expressionEvaluator);
    }

    @Override
    public void evaluate(DefineAction action, ScenarioExecutionContext context) {
        super.evaluate(action, context);

        Map<String, Object> facts = action.getDefine().getFacts();
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
    public Class<DefineAction> getSupportedType() {
        return DefineAction.class;
    }
}
