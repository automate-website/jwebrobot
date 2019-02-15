package website.automate.jwebrobot.executor.action;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.springframework.stereotype.Service;
import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.executor.ActionExecutorUtils;
import website.automate.jwebrobot.executor.ActionResult;
import website.automate.waml.io.model.main.action.ClickAction;

@Service
public class ClickActionExecutor extends BaseActionExecutor<ClickAction> {

    @Override
    public void execute(ClickAction action,
                        ScenarioExecutionContext context,
                        ActionResult result,
                        ActionExecutorUtils utils) {
        final WebDriver driver = context.getDriver();

        WebElement element =  (
            utils.getWebdriverWaitProvider().getInstance(
                driver,
                utils.getTimeoutResolver().resolve(action, context)
            )
        ).until(condition -> new WaitCondition(action, context, result, utils).apply(driver));

        if(element == WaitCondition.EMPTY){
            return;
        }

        Actions actions = new Actions(driver);
        actions.moveToElement(element).click().perform();

        result.setValue(element);
    }

    @Override
    public Class<ClickAction> getSupportedType() {
        return ClickAction.class;
    }

}
