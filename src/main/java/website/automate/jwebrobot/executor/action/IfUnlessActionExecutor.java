package website.automate.jwebrobot.executor.action;

import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.expression.ExpressionEvaluator;
import website.automate.jwebrobot.models.scenario.actions.Action;
import website.automate.jwebrobot.models.scenario.actions.IfUnlessAction;
import website.automate.jwebrobot.models.scenario.actions.criteria.Criterion;

public abstract class IfUnlessActionExecutor<T extends IfUnlessAction> extends BaseActionExecutor<T> {

    protected ExpressionEvaluator expressionEvaluator;
    
    public IfUnlessActionExecutor(ExpressionEvaluator expressionEvaluator){
        this.expressionEvaluator = expressionEvaluator;
    }
    
    @Override
    public void execute(Action action, ScenarioExecutionContext context){
        super.validate(action, context);
        T castedAction = (T)action;
        
        if(isExecutable(castedAction, context)){
            safeExecute(castedAction, context);
        }
    }
    
    private boolean isExecutable(T action, ScenarioExecutionContext context){
        boolean executeIf = evaluate(action.getIf(), context, true);
        boolean executeUnless = evaluate(action.getUnless(), context, false);
        
        return executeIf && !executeUnless;
    }
    
    private boolean evaluate(Criterion<String> criterion, ScenarioExecutionContext context, boolean defaultValue){
        if(criterion == null){
            return defaultValue;
        }
        String evaluationResultStr = expressionEvaluator.evaluate(criterion.getValue(), context.getMemory()).toString();
        return Boolean.parseBoolean(evaluationResultStr);
    }

}
