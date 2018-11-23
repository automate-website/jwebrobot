package website.automate.jwebrobot.executor.action;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;

import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.executor.ActionExecutorUtils;
import website.automate.jwebrobot.executor.ActionResult;
import website.automate.waml.io.model.main.action.EnterAction;

@Service
public class EnterActionExecutor implements ActionExecutor<EnterAction> {

    @Override
    public ActionResult execute(final EnterAction action,
                                final ScenarioExecutionContext context,
                                final ActionExecutorUtils utils) {
        WebDriver driver = context.getDriver();

        final WebElement element;
        
        boolean hasFilterCriteria = action.hasFilterCriteria();

        if (hasFilterCriteria) {
            element = (new WebDriverWait(driver, utils.getTimeoutResolver().resolve(action, context))).until(new ExpectedCondition<WebElement>() {
                public WebElement apply(WebDriver d) {
                    WebElement element = utils.getElementsFilter().filter(context, action);
                    utils.getElementStorage().store(action, context, element);
                    return element;
                }
            });
        } else {
            element = driver.switchTo().activeElement();
        }

        // Make clear
        if (Boolean.parseBoolean(action.getClear())) {
            element.clear();
        }

        /**
         * See {@link org.openqa.selenium.Keys} to send keys.
         */
        element.sendKeys(action.getInput().toString());

        return null;
    }

    @Override
    public Class<EnterAction> getSupportedType() {
        return EnterAction.class;
    }
}
