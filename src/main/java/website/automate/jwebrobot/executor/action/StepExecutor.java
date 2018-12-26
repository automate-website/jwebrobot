package website.automate.jwebrobot.executor.action;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.executor.ActionExecutorUtils;
import website.automate.jwebrobot.executor.ActionResult;
import website.automate.jwebrobot.expression.SpelExpressionEvaluator;
import website.automate.jwebrobot.listener.ExecutionEventListeners;
import website.automate.waml.io.model.main.action.Action;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Service
public class StepExecutor {

    private static final Logger LOGGER = LoggerFactory.getLogger(StepExecutor.class);

    private ExecutionEventListeners listener;

    private final ActionExecutorFactory actionExecutorFactory;

    private final ActionExecutorUtils utils;

    private SpelExpressionEvaluator expressionEvaluator;

    @Autowired
    public StepExecutor(ExecutionEventListeners listener,
                        ActionExecutorFactory actionExecutorFactory,
                        ActionExecutorUtils utils,
                        SpelExpressionEvaluator expressionEvaluator) {
        this.listener = listener;
        this.actionExecutorFactory = actionExecutorFactory;
        this.utils = utils;
        this.expressionEvaluator = expressionEvaluator;
    }
    
    public void execute(Action action, ScenarioExecutionContext context){
        ActionExecutor<Action> actionExecutor = getActionExecutor(action);

        listener.beforeAction(context, action);

        context.countStep(action);

        ActionResult result = actionExecutor.perform(action, context, utils);

        if(result.getCode() != ActionResult.SUCCESS
            && failedWhen(result, context)){
            throw new RuntimeException("Step execution has failed.", result.getError());
        }

        listener.afterAction(context, action);
    }

    private boolean failedWhen(ActionResult result, ScenarioExecutionContext context){
        Action action = result.getEvaluatedOrRawAction();
        if(action != null){
            String failedWhenStr = action.getFailed_when();
            if(isNotBlank(failedWhenStr)){
                return expressionEvaluator.evaluate(failedWhenStr, context.getTotalMemory(), Boolean.class);
            }
        }
        return false;
    }

    private ActionExecutor<Action> getActionExecutor(Action action){
        return actionExecutorFactory.getInstance(action.getClass());
    }
}
