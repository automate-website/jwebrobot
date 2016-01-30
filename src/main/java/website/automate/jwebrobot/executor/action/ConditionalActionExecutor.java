package website.automate.jwebrobot.executor.action;

import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.expression.ExpressionEvaluator;
import website.automate.jwebrobot.listener.ExecutionEventListeners;
import website.automate.jwebrobot.model.Action;
import website.automate.jwebrobot.model.CriteriaType;
import website.automate.jwebrobot.model.CriteriaValue;

public abstract class ConditionalActionExecutor extends BaseActionExecutor {

    protected ExpressionEvaluator expressionEvaluator;
    
    public ConditionalActionExecutor(ExpressionEvaluator expressionEvaluator,
            ExecutionEventListeners listener){
        super(listener);
        this.expressionEvaluator = expressionEvaluator;
    }
    
    @Override
    public boolean preHandle(Action action, ScenarioExecutionContext context){
        return isExecutable(action, context);
    }
    
    private boolean isExecutable(Action action, ScenarioExecutionContext context){
        boolean executeIf = evaluate(action.getCriteria(CriteriaType.IF), context, true);
        boolean executeUnless = evaluate(action.getCriteria(CriteriaType.UNLESS), context, false);
        
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
