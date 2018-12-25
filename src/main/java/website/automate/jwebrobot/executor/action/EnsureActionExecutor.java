package website.automate.jwebrobot.executor.action;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;
import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.executor.ActionExecutionResult.ActionResultBuilder;
import website.automate.jwebrobot.executor.ActionExecutionResult.StatusCode;
import website.automate.jwebrobot.executor.ActionExecutorUtils;
import website.automate.jwebrobot.executor.ActionExecutionResult;
import website.automate.waml.io.model.main.action.EnsureAction;

@Service
public class EnsureActionExecutor implements ActionExecutor<EnsureAction> {

    @Override
    public ActionExecutionResult execute(final EnsureAction action,
                                         final ScenarioExecutionContext context,
                                         final ActionExecutorUtils utils) {
        final WebDriver driver = context.getDriver();
        final ActionResultBuilder resultBuilder = new ActionResultBuilder();

        (new WebDriverWait(driver, utils.getTimeoutResolver().resolve(action, context))).until(d -> {
                WebElement webElement = utils.getElementsFilter().filter(context, action);
                if(webElement == null){
                    return false;
                } else {
                    resultBuilder.withValue(webElement);
                    return webElement.isDisplayed();
                }
        });

        return resultBuilder
            .build();
    }

    @Override
    public Class<EnsureAction> getSupportedType() {
        return EnsureAction.class;
    }
}
