package website.automate.jwebrobot.executor.action;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;
import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.executor.ActionExecutorUtils;
import website.automate.jwebrobot.executor.ActionResult;
import website.automate.waml.io.model.main.action.SelectAction;

@Service
public class SelectActionExecutor implements ActionExecutor<SelectAction> {

    private static final String OPTION = "option";

    @Override
    public ActionResult execute(final SelectAction action,
                                final ScenarioExecutionContext context,
                                final ActionExecutorUtils utils) {
        WebDriver driver = context.getDriver();

        WebElement element = (new WebDriverWait(driver, utils.getTimeoutResolver().resolve(action, context))).until(new ExpectedCondition<WebElement>() {
            public WebElement apply(WebDriver d) {
                return utils.getElementsFilter().filter(context, action);
            }
        });

        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();

        if (element.getTagName().equalsIgnoreCase(OPTION)) {
            element.click();
        } else {
            Select select = new Select(element);
            select.selectByValue(action.getSelect().getValue());
        }

        return null;
    }

    @Override
    public Class<SelectAction> getSupportedType() {
        return SelectAction.class;
    }

}
