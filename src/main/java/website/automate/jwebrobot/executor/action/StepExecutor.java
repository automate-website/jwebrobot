package website.automate.jwebrobot.executor.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.executor.ActionExecutorUtils;
import website.automate.jwebrobot.executor.ActionResult;
import website.automate.jwebrobot.executor.decorators.WithItemsExecutor;
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

        if(failedWhen(result, context)){
            throw new RuntimeException("Step execution has failed.", result.getError());
        }

        listener.afterAction(context, action);
    }

    private boolean failedWhen(ActionResult result, ScenarioExecutionContext context){
        Action action = result.getEvaluatedOrRawAction();
        if(action != null){
            String failedWhenStr = action.getFailedWhen();
            if(isNotBlank(failedWhenStr)){
                return expressionEvaluator.evaluate(failedWhenStr, context.getTotalMemory(), Boolean.class, false);
            }
        }
        return result.getCode() != ActionResult.SUCCESS;
    }

    private ActionExecutor<Action> getActionExecutor(Action action){
        return actionExecutorFactory.getInstance(action.getClass());
    }
}
