package website.automate.jwebrobot.expression;

import com.google.inject.Inject;

import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.waml.io.model.Scenario;
import website.automate.waml.io.model.action.ConditionalAction;

public class ConditionalExpressionEvaluator {

    private ExpressionEvaluator expressionEvaluator;
    
    @Inject
    public ConditionalExpressionEvaluator(ExpressionEvaluator expressionEvaluator){
        this.expressionEvaluator = expressionEvaluator;
    }
    
    public boolean isExecutable(Scenario scenario, ScenarioExecutionContext context){
        String whenCondition = scenario.getWhen();
        String unlessCondition = scenario.getUnless();
        return isExecutable(whenCondition, 
                unlessCondition, context);
    }
    
    public boolean isExecutable(ConditionalAction action, ScenarioExecutionContext context){
        return isExecutable(action.getWhen(), action.getUnless(), context);
    }
    
    private boolean isExecutable(String ifCriteriaValue, String unlessCriteriaValue, ScenarioExecutionContext context){
        boolean executeIf = evaluate(ifCriteriaValue, context, true);
        boolean executeUnless = evaluate(unlessCriteriaValue, context, false);
        
        return executeIf && !executeUnless;
    }
    
    private boolean evaluate(String value, ScenarioExecutionContext context, boolean defaultValue){
        if(value == null){
            return defaultValue;
        }
        String evaluationResultStr = expressionEvaluator.evaluate(value.toString(), context.getMemory()).toString();
        return Boolean.parseBoolean(evaluationResultStr);
    }
}
