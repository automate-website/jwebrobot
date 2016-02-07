package website.automate.jwebrobot.executor.action;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.expression.ExpressionEvaluator;
import website.automate.jwebrobot.listener.ExecutionEventListeners;
import website.automate.jwebrobot.model.Action;
import website.automate.jwebrobot.model.CriteriaValue;

public abstract class EvaluatedActionExecutor extends ConditionalActionExecutor {

    public EvaluatedActionExecutor(ExpressionEvaluator expressionEvaluator,
            ExecutionEventListeners listener) {
        super(expressionEvaluator, listener);
    }

    @Override
    public Action preprocess(Action action, ScenarioExecutionContext context){
        Action preprocessedAction = new Action();
        
        preprocessedAction.setType(action.getType());
        
        Map<String, CriteriaValue> criteriaValueMap = action.getCriteriaValueMap();
        
        Map<String, CriteriaValue> preprocessedCriteriaValueMap = new HashMap<>();
        preprocessedAction.setCriteriaValueMap(preprocessedCriteriaValueMap);
        
        preprocess(context, criteriaValueMap, preprocessedCriteriaValueMap);
        
        return preprocessedAction;
    }
    
    @SuppressWarnings("unchecked")
    private void preprocess(ScenarioExecutionContext context, Map<String, CriteriaValue> criteriaValueMap, Map<String, CriteriaValue> preprocessedCriteriaValueMap){
        for(Entry<String, CriteriaValue> criteriaValueEntry : criteriaValueMap.entrySet()){
            CriteriaValue criteriaValue = criteriaValueEntry.getValue();
            if(criteriaValue.asObject() instanceof Map){
                Map<String, CriteriaValue> nestedCriteriaValueMap = (Map<String, CriteriaValue>)criteriaValue.asObject();
                Map<String, CriteriaValue> nestedPreprocessedCriteriaValueMap = new HashMap<>();
                
                preprocessedCriteriaValueMap.put(criteriaValueEntry.getKey(), new CriteriaValue(nestedPreprocessedCriteriaValueMap));
                preprocess(context, nestedCriteriaValueMap, nestedPreprocessedCriteriaValueMap);
            } else {
                Object evaluatedExpression = expressionEvaluator.evaluate(criteriaValueEntry.getValue().asString(), context.getMemory());
                preprocessedCriteriaValueMap.put(criteriaValueEntry.getKey(), new CriteriaValue(evaluatedExpression));
            }
        }
    }
    
}
