package website.automate.jwebrobot.executor.action;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;

import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.executor.ActionExecutorUtils;
import website.automate.jwebrobot.executor.ActionResult;
import website.automate.waml.io.model.action.EnsureAction;

@Service
public class EnsureActionExecutor implements ActionExecutor<EnsureAction> {

    @Override
    public ActionResult execute(final EnsureAction action,
                                final ScenarioExecutionContext context,
                                final ActionExecutorUtils utils) {
        WebDriver driver = context.getDriver();

        final Boolean absent = Boolean.parseBoolean(action.getAbsent());
        (new WebDriverWait(driver, utils.getTimeoutResolver().resolve(action, context))).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                WebElement webElement = utils.getElementsFilter().filter(context, action);
                if(webElement == null){
                    return absent;
                } else {
                    utils.getElementStorage().store(action, context, webElement);

                    if(absent){
                        return Boolean.FALSE;
                    }
                    return webElement.isDisplayed();
                }
            }
        });

        return null;
    }

    @Override
    public Class<EnsureAction> getSupportedType() {
        return EnsureAction.class;
    }
}
