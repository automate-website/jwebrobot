package website.automate.jwebrobot.executor.action;

import org.openqa.selenium.TimeoutException;
import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.executor.ActionExecutorUtils;
import website.automate.jwebrobot.executor.ActionResult;
import website.automate.waml.io.model.main.action.Action;
import website.automate.waml.io.model.main.action.ConditionalAction;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

public abstract class BaseActionExecutor<T extends Action> implements ActionExecutor<T>  {

    private static final String FAILURE_MESSAGE_ELEMENT_NOT_FOUND = "Timeout waiting for an element matching given criteria.";

    abstract void execute(T action,
                          ScenarioExecutionContext context,
                          ActionResult result,
                          ActionExecutorUtils utils);

    public ActionResult perform(T action,
                                ScenarioExecutionContext context,
                                ActionExecutorUtils utils) {
        ActionResult result = new ActionResult();
        result.setRawAction(action);

        T evaluatedAction = null;
        try {
            if(isExecute(action, context, utils)) {
                evaluatedAction = utils.getActionEvaluator().evaluate(action, context);

                execute(evaluatedAction, context, result, utils);
            } else {
                result.setSkipped(true);
            }
        } catch (Exception e){
            translateException(result, e);
        } finally {
            result.setEvaluatedAction(evaluatedAction);
            register(context, result);
        }

        return result;
    }

    boolean isExecute(Action action,
                      ScenarioExecutionContext context,
                      ActionExecutorUtils utils){
        ConditionalAction conditionalAction = ConditionalAction.class.cast(action);
        return utils.getConditionalExpressionEvaluator().isExecutable(conditionalAction, context);
    }

    void register(ScenarioExecutionContext context, ActionResult result) {
        Action action = result.getEvaluatedOrRawAction();

        String register = action.getRegister();
        if (isNotBlank(register)) {
            context.getMemory().put(register, result);
        }
    }

    void translateException(ActionResult result, Exception e){
        result.setCode(ActionResult.ERROR);
        result.setMessage(e.getMessage());
        result.setError(e);

        if(e instanceof TimeoutException){
            result.setCode(ActionResult.FAILURE);
            result.setMessage(FAILURE_MESSAGE_ELEMENT_NOT_FOUND);
        }
    }
}
