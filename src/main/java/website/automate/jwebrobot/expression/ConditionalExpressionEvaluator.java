package website.automate.jwebrobot.expression;

import com.google.inject.Inject;

import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.model.Action;
import website.automate.jwebrobot.model.CriteriaType;
import website.automate.jwebrobot.model.CriteriaValue;
import website.automate.jwebrobot.model.Scenario;

public class ConditionalExpressionEvaluator {

    private ExpressionEvaluator expressionEvaluator;
    
    @Inject
    public ConditionalExpressionEvaluator(ExpressionEvaluator expressionEvaluator){
        this.expressionEvaluator = expressionEvaluator;
    }
    
    public boolean isExecutable(Scenario scenario, ScenarioExecutionContext context){
        String ifCondition = scenario.getIf();
        String unlessCondition = scenario.getUnless();
        return isExecutable(ifCondition != null ? new CriteriaValue(ifCondition) : null, 
                unlessCondition != null ? new CriteriaValue(unlessCondition) : null, context);
    }
    
    public boolean isExecutable(Action action, ScenarioExecutionContext context){
        return isExecutable(action.getCriteria(CriteriaType.IF), action.getCriteria(CriteriaType.UNLESS), context);
    }
    
    private boolean isExecutable(CriteriaValue ifCriteriaValue, CriteriaValue unlessCriteriaValue, ScenarioExecutionContext context){
        boolean executeIf = evaluate(ifCriteriaValue, context, true);
        boolean executeUnless = evaluate(unlessCriteriaValue, context, false);
        
        return executeIf && !executeUnless;
    }
    
    private boolean evaluate(CriteriaValue value, ScenarioExecutionContext context, boolean defaultValue){
        if(value == null){
            return defaultValue;
        }
        String evaluationResultStr = expressionEvaluator.evaluate(value.asString(), context.getMemory()).toString();
        return Boolean.parseBoolean(evaluationResultStr);
    }
}
