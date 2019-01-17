package website.automate.jwebrobot.executor.action;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.exceptions.GenericExecutionException;
import website.automate.jwebrobot.executor.ActionExecutorUtils;
import website.automate.jwebrobot.executor.ActionResult;
import website.automate.jwebrobot.executor.decorators.WithItemsExecutor;
import website.automate.jwebrobot.expression.SpelExpressionEvaluator;
import website.automate.jwebrobot.listener.ExecutionEventListeners;
import website.automate.jwebrobot.utils.SimpleNoNullValueStyle;
import website.automate.waml.io.model.main.Scenario;
import website.automate.waml.io.model.main.action.Action;
import website.automate.waml.io.model.main.action.IncludeAction;

import static java.text.MessageFormat.format;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Service
public class StepExecutor {

    private static final String TEMPLATE_ACTION_OK_LOG_MESSAGE = "ok: {0} > {1}{2}";

    private static final String TEMPLATE_ACTION_FAIL_LOG_MESSAGE = "fail: {0} > {1}{2}: {3}";

    private static final Logger LOGGER = LoggerFactory.getLogger(StepExecutor.class);

    private ExecutionEventListeners listener;

    private final ActionExecutorFactory actionExecutorFactory;

    private final ActionExecutorUtils utils;

    private SpelExpressionEvaluator expressionEvaluator;

    private WithItemsExecutor withItemsExecutor;

    @Autowired
    public StepExecutor(ExecutionEventListeners listener,
                        ActionExecutorFactory actionExecutorFactory,
                        ActionExecutorUtils utils,
                        SpelExpressionEvaluator expressionEvaluator,
                        WithItemsExecutor withItemsExecutor) {
        this.listener = listener;
        this.actionExecutorFactory = actionExecutorFactory;
        this.utils = utils;
        this.expressionEvaluator = expressionEvaluator;
        this.withItemsExecutor = withItemsExecutor;
    }

    public void execute(Action action, ScenarioExecutionContext context){
        if(withItemsExecutor.isApply(action)){
            withItemsExecutor.execute(action, context);
        } else {
            perform(action, context);
        }
    }
    
    private void perform(Action action, ScenarioExecutionContext context){
        ActionExecutor<Action> actionExecutor = getActionExecutor(action);

        listener.beforeAction(context, action);

        context.countStep(action);

        ActionResult result = actionExecutor.perform(action, context, utils);
        Action evaluatedAction = result.getEvaluatedOrRawAction();

        if(failedWhen(result, context)){
            LOGGER.error(getActionFailedLogMessage(
                context,
                context.getScenario(),
                evaluatedAction,
                result
            ));
            throw new GenericExecutionException(result.getMessage(), result.getError());
        } else {
            if(isLogActionSuccess(evaluatedAction)) {
                LOGGER.info(getActionOkLogMessage(
                    context,
                    context.getScenario(),
                    evaluatedAction
                ));
            }
        }

        listener.afterAction(context, action);
    }

    private boolean isLogActionSuccess(Action action){
        return !(action instanceof IncludeAction);
    }

    private String getActionFailedLogMessage(ScenarioExecutionContext context,
                                             Scenario scenario, Action action, ActionResult result){
        return format(TEMPLATE_ACTION_FAIL_LOG_MESSAGE,
            getScenarioPath(context, scenario),
            getActionName(action),
            getActionValue(action),
            result.getMessage());
    }

    private String getActionOkLogMessage(ScenarioExecutionContext context,
                                         Scenario scenario, Action action){
        return format(TEMPLATE_ACTION_OK_LOG_MESSAGE,
            getScenarioPath(context, scenario),
            getActionName(action),
            getActionValue(action));
    }

    private String getActionName(Action action){
        return action.getClass().getSimpleName().replaceFirst("Action", "");
    }

    private String getActionValue(Action action){
        return ReflectionToStringBuilder.toString(action, SimpleNoNullValueStyle.INSTANCE);
    }

    private boolean failedWhen(ActionResult result, ScenarioExecutionContext context){
        Action action = result.getEvaluatedOrRawAction();
        if(action != null){
            String failedWhenStr = action.getFailedWhen();
            if(isNotBlank(failedWhenStr)){
                return expressionEvaluator.evaluate(failedWhenStr, context.getTotalMemory(), Boolean.class);
            }
        }
        return !result.getCode().equalsIgnoreCase(ActionResult.SUCCESS);
    }

    private ActionExecutor<Action> getActionExecutor(Action action){
        return actionExecutorFactory.getInstance(action.getClass());
    }

    private String getScenarioPath(ScenarioExecutionContext context, Scenario scenario){
        String path = scenario.getName();
        ScenarioExecutionContext parentContext = context.getParent();
        while(parentContext != null){
            String parentName = parentContext.getScenario().getName();
            path = parentName + "/" + path;
            parentContext = parentContext.getParent();
        }
        return path;
    }
}
