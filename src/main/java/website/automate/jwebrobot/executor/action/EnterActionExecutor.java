package website.automate.jwebrobot.executor.action;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.inject.Inject;

import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.executor.filter.ElementFilterChain;
import website.automate.jwebrobot.expression.ExpressionEvaluator;
import website.automate.jwebrobot.listener.ExecutionEventListeners;
import website.automate.jwebrobot.model.Action;
import website.automate.jwebrobot.model.ActionType;
import website.automate.jwebrobot.model.CriteriaType;

public class EnterActionExecutor extends FilterActionExecutor {

    @Inject
    public EnterActionExecutor(ExpressionEvaluator expressionEvaluator,
            ExecutionEventListeners listener,
            ElementFilterChain elementFilterChain) {
        super(expressionEvaluator, listener,
                elementFilterChain);
    }

    @Override
    public ActionType getActionType() {
        return ActionType.ENTER;
    }

    @Override
    public void perform(final Action action, final ScenarioExecutionContext context) {
        WebDriver driver = context.getDriver();

        final WebElement element;
        
        boolean hasFilterCriteria = action.hasFilterCriteria();
        if (hasFilterCriteria) {
            element = (new WebDriverWait(driver, context.getTimeout())).until(new ExpectedCondition<WebElement>() {
                public WebElement apply(WebDriver d) {
                    return filter(context, action);
                }
            });
        } else {
            element = driver.switchTo().activeElement();
        }

        // Make clear
        if (action.getCriteria(CriteriaType.CLEAR) != null && action.getCriteria(CriteriaType.CLEAR).asBoolean()) {
            element.clear();
        }

        /**
         * See {@link org.openqa.selenium.Keys} to send keys.
         */
        element.sendKeys(action.getCriteria(CriteriaType.VALUE).asString());

    }

}
