package website.automate.jwebrobot.executor.action;

import java.util.Map;
import java.util.Map.Entry;

import com.google.inject.Inject;

import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.expression.ConditionalExpressionEvaluator;
import website.automate.jwebrobot.expression.ExpressionEvaluator;
import website.automate.jwebrobot.listener.ExecutionEventListeners;
import website.automate.waml.io.model.action.StoreAction;

public class StoreActionExecutor extends ConditionalActionExecutor<StoreAction> {

    @Inject
    public StoreActionExecutor(ExpressionEvaluator expressionEvaluator,
            ExecutionEventListeners listener,
            ConditionalExpressionEvaluator conditionalExpressionEvaluator) {
        super(expressionEvaluator, listener,
                conditionalExpressionEvaluator);
    }

    @Override
    public void perform(StoreAction action, ScenarioExecutionContext context) {
        Map<String, Object> memory = context.getMemory();
        
        Map<String, String> criteriaValueMap = action.getValue();
        
        for(Entry<String, String> criteriaValueEntry : criteriaValueMap.entrySet()){
            memory.put(criteriaValueEntry.getKey(), criteriaValueEntry.getValue());
        }
    }

    @Override
    public Class<StoreAction> getSupportedType() {
        return StoreAction.class;
    }

}
