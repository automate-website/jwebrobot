package website.automate.jwebrobot.executor.action;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Service;
import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.executor.ActionExecutorUtils;
import website.automate.jwebrobot.executor.ActionResult;
import website.automate.waml.io.model.main.action.EnsureAction;

@Service
public class EnsureActionExecutor extends BaseActionExecutor<EnsureAction> {

    @Override
    public void execute(EnsureAction action,
                        ScenarioExecutionContext context,
                        ActionResult result,
                        ActionExecutorUtils utils) {
        final WebDriver driver = context.getDriver();

        (utils.getWebdriverWaitProvider().getInstance(driver, utils.getTimeoutResolver().resolve(action, context))).until(d -> {
                WebElement webElement = utils.getElementsFilter().filter(context, action);
                if(webElement == null){
                    return false;
                } else {
                    result.setValue(webElement);
                    return webElement.isDisplayed();
                }
        });
    }

    @Override
    public Class<EnsureAction> getSupportedType() {
        return EnsureAction.class;
    }
}
