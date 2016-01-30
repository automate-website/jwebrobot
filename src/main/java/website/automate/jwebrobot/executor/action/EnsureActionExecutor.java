package website.automate.jwebrobot.executor.action;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.inject.Inject;

import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.expression.ExpressionEvaluator;
import website.automate.jwebrobot.listener.ExecutionEventListeners;
import website.automate.jwebrobot.model.Action;
import website.automate.jwebrobot.model.ActionType;

public class EnsureActionExecutor extends EvaluatedActionExecutor {

    @Inject
    public EnsureActionExecutor(ExpressionEvaluator expressionEvaluator,
            ExecutionEventListeners listener) {
        super(expressionEvaluator, listener);
    }

    @Override
    public ActionType getActionType() {
        return ActionType.ENSURE;
    }

    @Override
    public void perform(final Action action, ScenarioExecutionContext context) {
        WebDriver driver = context.getDriver();

        (new WebDriverWait(driver, context.getTimeout())).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                return d.findElement(By.cssSelector(action.getSelector())).isDisplayed();
            }
        });
    }

}
