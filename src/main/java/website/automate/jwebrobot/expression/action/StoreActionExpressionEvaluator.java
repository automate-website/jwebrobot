package website.automate.jwebrobot.expression.action;

import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.expression.ExpressionEvaluator;
import website.automate.waml.io.model.action.StoreAction;

import java.util.Map;
import java.util.Map.Entry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StoreActionExpressionEvaluator extends ConditionalActionExpressionEvaluator<StoreAction> {

    @Autowired
    public StoreActionExpressionEvaluator(
            ExpressionEvaluator expressionEvaluator) {
        super(expressionEvaluator);
    }

    @Override
    public void evaluate(StoreAction action, ScenarioExecutionContext context) {
        Map<String, String> value = action.getFacts();
        for(Entry<String, String> valueEntry : value.entrySet()){
            value.put(valueEntry.getKey(), evaluate(valueEntry.getValue(), context));
        }
    }

    @Override
    public Class<StoreAction> getSupportedType() {
        return StoreAction.class;
    }
}
