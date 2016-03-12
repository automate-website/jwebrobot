package website.automate.jwebrobot.expression.action;

import java.util.Map;
import java.util.Map.Entry;

import com.google.inject.Inject;

import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.expression.ExpressionEvaluator;
import website.automate.waml.io.model.CriterionValue;
import website.automate.waml.io.model.action.StoreAction;

public class StoreActionExpressionEvaluator extends ConditionalActionExpressionEvaluator<StoreAction> {

    @Inject
    public StoreActionExpressionEvaluator(
            ExpressionEvaluator expressionEvaluator) {
        super(expressionEvaluator);
    }

    @Override
    public void evaluate(StoreAction action, ScenarioExecutionContext context) {
        Map<String, CriterionValue> value = action.getValue();
        for(Entry<String, CriterionValue> valueEntry : value.entrySet()){
            value.put(valueEntry.getKey(), evaluate(valueEntry.getValue(), context));
        }
    }

    @Override
    public Class<StoreAction> getSupportedType() {
        return StoreAction.class;
    }
}
