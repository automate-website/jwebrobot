package website.automate.jwebrobot.executor.action;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;
import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.executor.ActionExecutorUtils;
import website.automate.jwebrobot.executor.ActionResult;
import website.automate.waml.io.model.main.action.MoveAction;

@Service
public class MoveActionExecutor implements ActionExecutor<MoveAction> {

    @Override
    public ActionResult execute(final MoveAction action,
                                final ScenarioExecutionContext context,
                                final ActionExecutorUtils utils) {
        WebDriver driver = context.getDriver();

        WebElement element = (new WebDriverWait(driver, utils.getTimeoutResolver().resolve(action, context))).until(new ExpectedCondition<WebElement>() {
            public WebElement apply(WebDriver d) {
                WebElement element = utils.getElementsFilter().filter(context, action);
                return element;
            }
        });

        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();

        return null;
    }

    @Override
    public Class<MoveAction> getSupportedType() {
       return MoveAction.class;
    }

}
