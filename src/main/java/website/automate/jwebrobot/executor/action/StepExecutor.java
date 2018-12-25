package website.automate.jwebrobot.executor.action;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.exceptions.ExceptionTranslator;
import website.automate.jwebrobot.executor.ActionExecutorUtils;
import website.automate.jwebrobot.executor.ActionExecutionResult;
import website.automate.jwebrobot.expression.ConditionalExpressionEvaluator;
import website.automate.jwebrobot.listener.ExecutionEventListeners;
import website.automate.jwebrobot.utils.SimpleNoNullValueStyle;
import website.automate.waml.io.model.main.Scenario;
import website.automate.waml.io.model.main.action.Action;
import website.automate.waml.io.model.main.action.ConditionalAction;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Service
public class StepExecutor {

    private static final Logger LOGGER = LoggerFactory.getLogger(StepExecutor.class);

    private ExecutionEventListeners listener;

    private ExceptionTranslator exceptionTranslator;

    private final ActionExecutorFactory actionExecutorFactory;

    private final ConditionalExpressionEvaluator conditionalExpressionEvaluator;

    private final ActionExecutorUtils actionExecutorUtils;

    private final ActionPreprocessor actionPreprocessor;

    @Autowired
    public StepExecutor(ExecutionEventListeners listener,
                        ExceptionTranslator exceptionTranslator,
                        ActionExecutorFactory actionExecutorFactory,
                        ConditionalExpressionEvaluator conditionalExpressionEvaluator,
                        ActionExecutorUtils actionExecutorUtils,
                        ActionPreprocessor actionPreprocessor) {
        this.listener = listener;
        this.exceptionTranslator = exceptionTranslator;
        this.actionExecutorFactory = actionExecutorFactory;
        this.conditionalExpressionEvaluator = conditionalExpressionEvaluator;
        this.actionExecutorUtils = actionExecutorUtils;
        this.actionPreprocessor = actionPreprocessor;
    }
    
    public void execute(Action action, ScenarioExecutionContext context){
        ActionExecutor<Action> actionExecutor = getActionExecutor(action);

        listener.beforeAction(context, action);

        context.countStep(action);

        try {
            ActionExecutionResult result;
            if(isExecute(action, context)){
                Action preprocessedAction = actionPreprocessor.preprocess(action, context);
                LOGGER.info(getActionLogMessage(context.getScenario(), preprocessedAction));
                result = actionExecutor.execute(preprocessedAction, context, actionExecutorUtils);
                registerResultIfRequired(preprocessedAction, result, context);
            }

        } catch (RuntimeException e) {
        	RuntimeException translatedException = exceptionTranslator.translate(e);
            listener.errorAction(context, action, translatedException);
            throw translatedException;
        }
        
        listener.afterAction(context, action);
    }


    private void registerResultIfRequired(Action action, ActionExecutionResult result,
                                          ScenarioExecutionContext context){
        String register = action.getRegister();
        if(isNotBlank(register)){
            context.getMemory().put(register, result.getValue());
        }
    }

    private boolean isExecute(Action action, ScenarioExecutionContext context){
        ConditionalAction conditionalAction = ConditionalAction.class.cast(action);
        return conditionalExpressionEvaluator.isExecutable(conditionalAction, context);
    }

    private ActionExecutor<Action> getActionExecutor(Action action){
        return actionExecutorFactory.getInstance(action.getClass());
    }

    private String getActionLogMessage(Scenario scenario, Action action){
        return scenario.getName() + " > " + getActionName(action) + getActionValue(action);
    }

    private String getActionName(Action action){
        return action.getClass().getSimpleName().replaceFirst("Action", "");
    }

    private String getActionValue(Action action){
        return ReflectionToStringBuilder.toString(action, SimpleNoNullValueStyle.INSTANCE);
    }
}
