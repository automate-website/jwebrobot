package website.automate.jwebrobot.executor.action;

import com.google.inject.Inject;

import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.expression.ExpressionEvaluator;
import website.automate.jwebrobot.model.Action;
import website.automate.jwebrobot.model.ActionType;

public class WaitActionExecutor extends IfUnlessActionExecutor {

    @Inject
    public WaitActionExecutor(ExpressionEvaluator expressionEvaluator) {
        super(expressionEvaluator);
    }

    @Override
    public ActionType getActionType() {
        return ActionType.WAIT;
    }

    @Override
    public void perform(final Action action, ScenarioExecutionContext context) {
        long time = Long.parseLong(action.getTime());
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {

        }

    }

}
