package website.automate.jwebrobot.executor.action;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.expression.ExpressionEvaluator;
import website.automate.jwebrobot.model.Action;
import website.automate.jwebrobot.model.CriteriaValue;

public abstract class EvaluatedActionExecutor extends ConditionalActionExecutor {

    public EvaluatedActionExecutor(ExpressionEvaluator expressionEvaluator) {
        super(expressionEvaluator);
    }

    @Override
    public Action preprocess(Action action, ScenarioExecutionContext context){
        Action preprocessedAction = new Action();
        
        preprocessedAction.setType(action.getType());
        
        Map<String, CriteriaValue> criteriaValueMap = action.getCriteriaValueMap();
        
        Map<String, CriteriaValue> preprocessedCriteriaValueMap = new HashMap<>();
        preprocessedAction.setCriteriaValueMap(preprocessedCriteriaValueMap);
        
        for(Entry<String, CriteriaValue> criteriaValueEntry : criteriaValueMap.entrySet()){
            Object evaluatedExpression = expressionEvaluator.evaluate(criteriaValueEntry.getValue().asString(), context.getMemory());
            preprocessedCriteriaValueMap.put(criteriaValueEntry.getKey(), new CriteriaValue(evaluatedExpression));
        }
        
        return preprocessedAction;
    }
    
}
