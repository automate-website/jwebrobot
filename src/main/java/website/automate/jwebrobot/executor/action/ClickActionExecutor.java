package website.automate.jwebrobot.executor.action;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;

import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.executor.ActionExecutorUtils;
import website.automate.jwebrobot.executor.ActionResult;
import website.automate.waml.io.model.action.ClickAction;

@Service
public class ClickActionExecutor implements ActionExecutor<ClickAction> {

    @Override
    public ActionResult execute(final ClickAction action,
                                final ScenarioExecutionContext context,
                                final ActionExecutorUtils utils) {
        WebDriver driver = context.getDriver();

        WebElement element =  (
            new WebDriverWait(
                driver,
                utils.getTimeoutResolver().resolve(action, context)
            )
        ).until(condition -> utils.getElementsFilter().filter(context, action));

        utils.getElementStorage().store(action, context, element);

        Actions actions = new Actions(driver);
        actions.moveToElement(element).click().perform();

        return null;
    }

    @Override
    public Class<ClickAction> getSupportedType() {
        return ClickAction.class;
    }

}
