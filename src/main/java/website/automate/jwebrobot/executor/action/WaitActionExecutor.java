package website.automate.jwebrobot.executor.action;

import com.google.inject.Inject;

import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.expression.ConditionalExpressionEvaluator;
import website.automate.jwebrobot.expression.ExpressionEvaluator;
import website.automate.jwebrobot.listener.ExecutionEventListeners;
import website.automate.waml.io.model.action.WaitAction;

public class WaitActionExecutor extends ConditionalActionExecutor<WaitAction> {

    @Inject
    public WaitActionExecutor(ExpressionEvaluator expressionEvaluator,
            ExecutionEventListeners listener,
            ConditionalExpressionEvaluator conditionalExpressionEvaluator) {
        super(expressionEvaluator, listener,
                conditionalExpressionEvaluator);
    }

    @Override
    public void perform(final WaitAction action, ScenarioExecutionContext context) {
        Long time = Long.parseLong(action.getTime());
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {

        }

    }

    @Override
    public Class<WaitAction> getSupportedType() {
        return WaitAction.class;
    }

}
