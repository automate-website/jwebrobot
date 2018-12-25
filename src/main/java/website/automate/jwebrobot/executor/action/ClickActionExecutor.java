package website.automate.jwebrobot.executor.action;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;
import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.executor.ActionExecutionResult.ActionResultBuilder;
import website.automate.jwebrobot.executor.ActionExecutorUtils;
import website.automate.jwebrobot.executor.ActionExecutionResult;
import website.automate.waml.io.model.main.action.ClickAction;

@Service
public class ClickActionExecutor implements ActionExecutor<ClickAction> {

    @Override
    public ActionExecutionResult execute(final ClickAction action,
                                         final ScenarioExecutionContext context,
                                         final ActionExecutorUtils utils) {
        WebDriver driver = context.getDriver();
        final ActionResultBuilder resultBuilder = new ActionResultBuilder();

        WebElement element =  (
            new WebDriverWait(
                driver,
                utils.getTimeoutResolver().resolve(action, context)
            )
        ).until(condition -> utils.getElementsFilter().filter(context, action));

        Actions actions = new Actions(driver);
        actions.moveToElement(element).click().perform();

        return resultBuilder
            .withValue(element)
            .build();
    }

    @Override
    public Class<ClickAction> getSupportedType() {
        return ClickAction.class;
    }

}
