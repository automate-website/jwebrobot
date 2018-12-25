package website.automate.jwebrobot.executor.action;

import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.exceptions.ExceptionTranslator;
import website.automate.jwebrobot.executor.ActionExecutorUtils;
import website.automate.jwebrobot.executor.ActionResult;
import website.automate.jwebrobot.expression.ConditionalExpressionEvaluator;
import website.automate.jwebrobot.listener.ExecutionEventListeners;
import website.automate.waml.io.model.main.action.Action;
import website.automate.waml.io.model.main.action.ConditionalAction;

public abstract class StepExecutor {

    private ExecutionEventListeners listener;

    private ExceptionTranslator exceptionTranslator;

    private final ActionExecutorFactory actionExecutorFactory;

    private final ConditionalExpressionEvaluator conditionalExpressionEvaluator;

    private final ActionExecutorUtils actionExecutorUtils;

    public StepExecutor(ExecutionEventListeners listener,
                        ExceptionTranslator exceptionTranslator,
                        ActionExecutorFactory actionExecutorFactory,
                        ConditionalExpressionEvaluator conditionalExpressionEvaluator,
                        ActionExecutorUtils actionExecutorUtils) {
        this.listener = listener;
        this.exceptionTranslator = exceptionTranslator;
        this.actionExecutorFactory = actionExecutorFactory;
        this.conditionalExpressionEvaluator = conditionalExpressionEvaluator;
        this.actionExecutorUtils = actionExecutorUtils;
    }
    
    public void execute(Action action, ScenarioExecutionContext context){
        ActionExecutor<Action> actionExecutor = getActionExecutor(action);

        listener.beforeAction(context, action);

        context.countStep(action);

        try {
            ActionResult result;
            if(isExecute(action, context)){
                result = actionExecutor.execute(action, context, actionExecutorUtils);
            }

        } catch (RuntimeException e) {
        	RuntimeException translatedException = exceptionTranslator.translate(e);
            listener.errorAction(context, action, translatedException);
            throw translatedException;
        }
        
        listener.afterAction(context, action);
    }

    private boolean isExecute(Action action, ScenarioExecutionContext context){
        ConditionalAction conditionalAction = ConditionalAction.class.cast(action);
        return conditionalExpressionEvaluator.isExecutable(conditionalAction, context);
    }

    private ActionExecutor<Action> getActionExecutor(Action action){
        return actionExecutorFactory.getInstance(action.getClass());
    }
}
