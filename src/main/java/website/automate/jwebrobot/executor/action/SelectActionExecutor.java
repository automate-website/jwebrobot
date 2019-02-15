package website.automate.jwebrobot.executor.action;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.springframework.stereotype.Service;
import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.executor.ActionExecutorUtils;
import website.automate.jwebrobot.executor.ActionResult;
import website.automate.waml.io.model.main.action.SelectAction;

@Service
public class SelectActionExecutor extends BaseActionExecutor<SelectAction> {

    private static final String OPTION = "option";

    @Override
    public void execute(SelectAction action,
                        ScenarioExecutionContext context,
                        ActionResult result,
                        ActionExecutorUtils utils) {
        final WebDriver driver = context.getDriver();

        WebElement element = (utils.getWebdriverWaitProvider().getInstance(driver, utils.getTimeoutResolver().resolve(action, context)))
            .until(condition -> new WaitCondition(action, context, result, utils).apply(driver));

        if(element == WaitCondition.EMPTY){
            return;
        }

        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();

        if (element.getTagName().equalsIgnoreCase(OPTION)) {
            element.click();
        } else {
            Select select = new Select(element);
            select.selectByValue(action.getSelect().getValue());
        }

        result.setValue(element);
    }

    @Override
    public Class<SelectAction> getSupportedType() {
        return SelectAction.class;
    }

}
