package website.automate.jwebrobot.executor.action;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.models.scenario.actions.EnterAction;

public class EnterActionExecutor extends BaseActionExecutor<EnterAction> {

    @Override
    public Class<EnterAction> getActionType() {
        return EnterAction.class;
    }

    @Override
    public void safeExecute(final EnterAction action, ScenarioExecutionContext context) {
        WebDriver driver = context.getDriver();

        final WebElement element;

        if (action.getSelector() != null) {
            final By selector = By.cssSelector(action.getSelector().getValue());
            element = (new WebDriverWait(driver, context.getTimeout())).until(new ExpectedCondition<WebElement>() {
                public WebElement apply(WebDriver d) {
                    return d.findElement(selector);
                }
            });
        } else {
            element = driver.switchTo().activeElement();
        }

        // Make clear
        if (action.getClear() != null && action.getClear().getValue()) {
            element.clear();
        }

        /**
         * See {@link org.openqa.selenium.Keys} to send keys.
         */
        element.sendKeys(action.getValue().getValue());

    }

}
