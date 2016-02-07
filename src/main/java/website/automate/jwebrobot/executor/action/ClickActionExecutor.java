package website.automate.jwebrobot.executor.action;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.inject.Inject;

import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.executor.filter.ElementFilterChain;
import website.automate.jwebrobot.expression.ExpressionEvaluator;
import website.automate.jwebrobot.listener.ExecutionEventListeners;
import website.automate.jwebrobot.model.Action;
import website.automate.jwebrobot.model.ActionType;

public class ClickActionExecutor extends FilterActionExecutor {

    @Inject
    public ClickActionExecutor(ExpressionEvaluator expressionEvaluator, ExecutionEventListeners listener,
            ElementFilterChain elementFilterChain) {
        super(expressionEvaluator, listener, elementFilterChain);
    }

    @Override
    public ActionType getActionType() {
        return ActionType.CLICK;
    }

    @Override
    public void perform(final Action action, final ScenarioExecutionContext context) {
        WebDriver driver = context.getDriver();

        WebElement element = (new WebDriverWait(driver, getActionTimeout(action, context))).until(new ExpectedCondition<WebElement>() {
            public WebElement apply(WebDriver d) {
                return filter(context, action);
            }
        });

        Actions actions = new Actions(driver);
        actions.moveToElement(element).click().perform();
    }

}
