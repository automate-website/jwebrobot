package website.automate.jwebrobot.executor.action;

import org.openqa.selenium.WebDriver;

import com.google.inject.Inject;

import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.expression.ExpressionEvaluator;
import website.automate.jwebrobot.models.scenario.actions.OpenAction;

public class OpenActionExecutor extends IfUnlessActionExecutor<OpenAction> {

    @Inject
    public OpenActionExecutor(ExpressionEvaluator expressionEvaluator) {
        super(expressionEvaluator);
    }

    @Override
    public Class<OpenAction> getActionType() {
        return OpenAction.class;
    }

    @Override
    public void safeExecute(final OpenAction action, ScenarioExecutionContext context) {
        WebDriver driver = context.getDriver();
        driver.get(action.getUrl().getValue());
    }

}
