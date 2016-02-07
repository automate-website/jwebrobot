package website.automate.jwebrobot.executor.action;

import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.expression.ConditionalExpressionEvaluator;
import website.automate.jwebrobot.expression.ExpressionEvaluator;
import website.automate.jwebrobot.listener.ExecutionEventListeners;
import website.automate.jwebrobot.model.Action;

public abstract class ConditionalActionExecutor extends BaseActionExecutor {

    protected ExpressionEvaluator expressionEvaluator;
    
    private ConditionalExpressionEvaluator conditionalExpressionEvaluator;
    
    public ConditionalActionExecutor(ExpressionEvaluator expressionEvaluator,
            ExecutionEventListeners listener,
            ConditionalExpressionEvaluator conditionalExpressionEvaluator){
        super(listener);
        this.expressionEvaluator = expressionEvaluator;
        this.conditionalExpressionEvaluator = conditionalExpressionEvaluator;
    }
    
    @Override
    public boolean preHandle(Action action, ScenarioExecutionContext context){
        return conditionalExpressionEvaluator.isExecutable(action, context);
    }
}
