package website.automate.jwebrobot.executor.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.exceptions.DecimalNumberExpectedException;
import website.automate.jwebrobot.exceptions.ExceptionTranslator;
import website.automate.jwebrobot.exceptions.WaitTimeTooBigException;
import website.automate.jwebrobot.expression.ConditionalExpressionEvaluator;
import website.automate.jwebrobot.expression.ExpressionEvaluator;
import website.automate.jwebrobot.listener.ExecutionEventListeners;
import website.automate.waml.io.model.action.WaitAction;

@Service
public class WaitActionExecutor extends ConditionalActionExecutor<WaitAction> {

    private static final int WAIT_TIME_LIMIT = 1000;

    @Autowired
    public WaitActionExecutor(ExpressionEvaluator expressionEvaluator,
            ExecutionEventListeners listener,
            ConditionalExpressionEvaluator conditionalExpressionEvaluator,
            ExceptionTranslator exceptionTranslator) {
        super(expressionEvaluator, listener,
                conditionalExpressionEvaluator,
                exceptionTranslator);
    }

    @Override
    public void perform(final WaitAction action, ScenarioExecutionContext context) {
        String waitTimeStr = action.getTime();
        try {
            Double waitTime = Double.parseDouble(waitTimeStr);

            if (waitTime >= WAIT_TIME_LIMIT) {
                throw new WaitTimeTooBigException(action.getClass(), waitTime);
            }

            Thread.sleep(Math.round(waitTime * 1000));
        } catch (NumberFormatException e) {
            throw new DecimalNumberExpectedException(action.getClass(), waitTimeStr);
        } catch (InterruptedException e) {
            // This should never happen
        }
    }

    @Override
    public Class<WaitAction> getSupportedType() {
        return WaitAction.class;
    }

}
