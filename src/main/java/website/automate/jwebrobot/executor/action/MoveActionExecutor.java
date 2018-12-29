package website.automate.jwebrobot.executor.action;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;
import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.executor.ActionExecutorUtils;
import website.automate.jwebrobot.executor.ActionResult;
import website.automate.waml.io.model.main.action.MoveAction;

@Service
public class MoveActionExecutor extends BaseActionExecutor<MoveAction> {

    @Override
    public void execute(MoveAction action,
                        ScenarioExecutionContext context,
                        ActionResult result,
                        ActionExecutorUtils utils) {
        final WebDriver driver = context.getDriver();

        final WebElement element = (utils.getWebdriverWaitProvider().getInstance(driver, utils.getTimeoutResolver().resolve(action, context)))
            .until(d -> utils.getElementsFilter().filter(context, action));

        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();

        result.setValue(element);
    }

    @Override
    public Class<MoveAction> getSupportedType() {
       return MoveAction.class;
    }

}
