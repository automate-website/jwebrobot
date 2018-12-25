package website.automate.jwebrobot.executor.action;

import org.springframework.stereotype.Service;
import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.exceptions.DecimalNumberExpectedException;
import website.automate.jwebrobot.exceptions.WaitTimeTooBigException;
import website.automate.jwebrobot.executor.ActionExecutorUtils;
import website.automate.jwebrobot.executor.ActionResult;
import website.automate.waml.io.model.main.action.WaitAction;

@Service
public class WaitActionExecutor extends BaseActionExecutor<WaitAction> {

    private static final int WAIT_TIME_LIMIT = 1000;

    @Override
    public void execute(WaitAction action,
                        ScenarioExecutionContext context,
                        ActionResult result,
                        ActionExecutorUtils utils) {
        String waitTimeStr = action.getWait().getTime();
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
