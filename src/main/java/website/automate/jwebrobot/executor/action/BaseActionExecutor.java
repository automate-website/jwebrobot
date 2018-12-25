package website.automate.jwebrobot.executor.action;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.openqa.selenium.TimeoutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.executor.ActionResult;
import website.automate.jwebrobot.executor.ActionResult.StatusCode;
import website.automate.jwebrobot.executor.ActionExecutorUtils;
import website.automate.jwebrobot.utils.SimpleNoNullValueStyle;
import website.automate.waml.io.model.main.Scenario;
import website.automate.waml.io.model.main.action.Action;
import website.automate.waml.io.model.main.action.ConditionalAction;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

public abstract class BaseActionExecutor<T extends Action> implements ActionExecutor<T>  {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseActionExecutor.class);

    private static final String FAILURE_MESSAGE_ELEMENT_NOT_FOUND = "Timeout waiting for an element matching given criteria.";

    abstract void execute(T action,
                          ScenarioExecutionContext context,
                          ActionResult result,
                          ActionExecutorUtils utils);

    public ActionResult perform(T action,
                                ScenarioExecutionContext context,
                                ActionExecutorUtils utils) {
        ActionResult result = new ActionResult();

        T evaluatedAction = null;
        try {
            if(isExecute(action, context, utils)) {
                evaluatedAction = utils.getActionEvaluator().evaluate(action, context);

                LOGGER.info(getActionLogMessage(context.getScenario(), evaluatedAction));

                execute(evaluatedAction, context, result, utils);
            } else {
            }
        } catch (Exception e){
            translateException(result, e);
        } finally {
            if(evaluatedAction != null) {
                register(evaluatedAction, context, result);
            } else {
                register(action, context, result);
            }
        }

        return result;
    }

    boolean isExecute(Action action,
                      ScenarioExecutionContext context,
                      ActionExecutorUtils utils){
        ConditionalAction conditionalAction = ConditionalAction.class.cast(action);
        return utils.getConditionalExpressionEvaluator().isExecutable(conditionalAction, context);
    }

    void register(T action, ScenarioExecutionContext context, ActionResult result) {
        String register = action.getRegister();
        if (isNotBlank(register)) {
            context.getMemory().put(register, result);
        }
    }

    void translateException(ActionResult result, Exception e){
        result.setCode(StatusCode.ERROR);
        result.setMessage(e.getMessage());
        result.setError(e);

        if(e instanceof TimeoutException){
            result.setCode(StatusCode.FAILURE);
            result.setMessage(FAILURE_MESSAGE_ELEMENT_NOT_FOUND);
        }
    }

    String getActionLogMessage(Scenario scenario, Action action){
        return scenario.getName() + " > " + getActionName(action) + getActionValue(action);
    }

    private String getActionName(Action action){
        return action.getClass().getSimpleName().replaceFirst("Action", "");
    }

    private String getActionValue(Action action){
        return ReflectionToStringBuilder.toString(action, SimpleNoNullValueStyle.INSTANCE);
    }
}
