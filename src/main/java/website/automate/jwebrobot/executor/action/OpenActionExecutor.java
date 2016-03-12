package website.automate.jwebrobot.executor.action;

import org.openqa.selenium.WebDriver;

import com.google.inject.Inject;

import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.expression.ConditionalExpressionEvaluator;
import website.automate.jwebrobot.expression.ExpressionEvaluator;
import website.automate.jwebrobot.listener.ExecutionEventListeners;
import website.automate.waml.io.model.action.OpenAction;

public class OpenActionExecutor extends ConditionalActionExecutor<OpenAction> {

    @Inject
    public OpenActionExecutor(ExpressionEvaluator expressionEvaluator,
            ExecutionEventListeners listener,
            ConditionalExpressionEvaluator conditionalExpressionEvaluator) {
        super(expressionEvaluator, listener,
                conditionalExpressionEvaluator);
    }

    @Override
    public void perform(final OpenAction action, ScenarioExecutionContext context) {
        WebDriver driver = context.getDriver();
        driver.get(action.getUrl());
    }

    @Override
    public Class<OpenAction> getSupportedType() {
        return OpenAction.class;
    }

}
