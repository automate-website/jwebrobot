package website.automate.jwebrobot.expression.action;

import com.google.inject.Inject;
import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.expression.ExpressionEvaluator;
import website.automate.waml.io.model.action.StoreAction;

import java.util.Map;
import java.util.Map.Entry;

public class StoreActionExpressionEvaluator extends ConditionalActionExpressionEvaluator<StoreAction> {

    @Inject
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
