package website.automate.jwebrobot.executor.action;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;
import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.executor.ActionExecutionResult.ActionResultBuilder;
import website.automate.jwebrobot.executor.ActionExecutorUtils;
import website.automate.jwebrobot.executor.ActionExecutionResult;
import website.automate.waml.io.model.main.action.EnterAction;

@Service
public class EnterActionExecutor implements ActionExecutor<EnterAction> {

    @Override
    public ActionExecutionResult execute(final EnterAction action,
                                         final ScenarioExecutionContext context,
                                         final ActionExecutorUtils utils) {
        final WebDriver driver = context.getDriver();
        final ActionResultBuilder resultBuilder = new ActionResultBuilder();
        final WebElement element;
        
        boolean hasFilterCriteria = action.getFilter().hasFilterCriteria();

        if (hasFilterCriteria) {
            element = (new WebDriverWait(driver, utils.getTimeoutResolver().resolve(action, context)))
                .until(d -> utils.getElementsFilter().filter(context, action));
        } else {
            element = driver.switchTo().activeElement();
        }

        // Make clear
        if (Boolean.parseBoolean(action.getEnter().getClear())) {
            element.clear();
        }

        /**
         * See {@link org.openqa.selenium.Keys} to send keys.
         */
        element.sendKeys(action.getEnter().getInput());

        return resultBuilder
            .withValue(element)
            .build();
    }

    @Override
    public Class<EnterAction> getSupportedType() {
        return EnterAction.class;
    }
}
