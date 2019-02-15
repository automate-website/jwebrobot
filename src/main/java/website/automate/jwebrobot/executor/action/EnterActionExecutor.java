package website.automate.jwebrobot.executor.action;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Service;
import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.executor.ActionExecutorUtils;
import website.automate.jwebrobot.executor.ActionResult;
import website.automate.waml.io.model.main.action.EnterAction;

@Service
public class EnterActionExecutor extends BaseActionExecutor<EnterAction> {

    @Override
    public void execute(EnterAction action,
                        ScenarioExecutionContext context,
                        ActionResult result,
                        ActionExecutorUtils utils) {
        final WebDriver driver = context.getDriver();
        final WebElement element;
        
        boolean hasFilterCriteria = action.getFilter().hasFilterCriteria();

        if (hasFilterCriteria) {
            element = (utils.getWebdriverWaitProvider().getInstance(driver, utils.getTimeoutResolver().resolve(action, context)))
                .until(condition -> new WaitCondition(action, context, result, utils).apply(driver));
        } else {
            element = driver.switchTo().activeElement();
        }

        if(element == WaitCondition.EMPTY){
            return;
        }

        // Make clear
        if (Boolean.parseBoolean(action.getEnter().getClear())) {
            element.clear();
        }

        /**
         * See {@link org.openqa.selenium.Keys} to send keys.
         */
        element.sendKeys(action.getEnter().getInput());

        result.setValue(element);
    }

    @Override
    public Class<EnterAction> getSupportedType() {
        return EnterAction.class;
    }
}
