package website.automate.jwebrobot.executor.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.executor.ActionExecutorUtils;
import website.automate.jwebrobot.executor.ActionResult;
import website.automate.jwebrobot.listener.ExecutionEventListeners;
import website.automate.waml.io.model.main.action.Action;

@Service
public class StepExecutor {

    private static final Logger LOGGER = LoggerFactory.getLogger(StepExecutor.class);

    private ExecutionEventListeners listener;

    private final ActionExecutorFactory actionExecutorFactory;

    private final ActionExecutorUtils utils;

    @Autowired
    public StepExecutor(ExecutionEventListeners listener,
                        ActionExecutorFactory actionExecutorFactory,
                        ActionExecutorUtils utils) {
        this.listener = listener;
        this.actionExecutorFactory = actionExecutorFactory;
        this.utils = utils;
    }
    
    public void execute(Action action, ScenarioExecutionContext context){
        ActionExecutor<Action> actionExecutor = getActionExecutor(action);

        listener.beforeAction(context, action);

        context.countStep(action);

        ActionResult result = actionExecutor.perform(action, context, utils);

        if(result.getCode() == ActionResult.StatusCode.ERROR){
            throw new RuntimeException("Step execution has failed.", result.getError());
        } else if(result.getCode() == ActionResult.StatusCode.FAILURE){
            LOGGER.error(result.getMessage());
            System.exit(1);
        }

        listener.afterAction(context, action);
    }

    private ActionExecutor<Action> getActionExecutor(Action action){
        return actionExecutorFactory.getInstance(action.getClass());
    }
}
