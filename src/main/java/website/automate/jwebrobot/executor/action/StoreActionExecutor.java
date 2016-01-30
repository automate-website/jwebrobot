package website.automate.jwebrobot.executor.action;

import java.util.Map;
import java.util.Map.Entry;

import com.google.inject.Inject;

import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.expression.ExpressionEvaluator;
import website.automate.jwebrobot.listener.ExecutionEventListeners;
import website.automate.jwebrobot.model.Action;
import website.automate.jwebrobot.model.ActionType;
import website.automate.jwebrobot.model.CriteriaValue;

public class StoreActionExecutor extends EvaluatedActionExecutor {

    @Inject
    public StoreActionExecutor(ExpressionEvaluator expressionEvaluator,
            ExecutionEventListeners listener) {
        super(expressionEvaluator, listener);
    }

    @Override
    public ActionType getActionType() {
        return ActionType.STORE;
    }

    @Override
    public void perform(Action action, ScenarioExecutionContext context) {
        Map<String, Object> memory = context.getMemory();
        
        Map<String, CriteriaValue> criteriaValueMap = action.getCriteriaValueMap();
        
        for(Entry<String, CriteriaValue> criteriaValueEntry : criteriaValueMap.entrySet()){
            memory.put(criteriaValueEntry.getKey(), criteriaValueEntry.getValue().asObject());
        }
    }

}
