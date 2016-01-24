package website.automate.jwebrobot.executor.action;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.models.scenario.actions.SelectAction;

public class SelectActionExecutor extends BaseActionExecutor<SelectAction> {

    private static final String OPTION = "option";

    @Override
    public Class<SelectAction> getActionType() {
        return SelectAction.class;
    }

    @Override
    public void safeExecute(final SelectAction action, ScenarioExecutionContext context) {
        WebDriver driver = context.getDriver();

        WebElement element = (new WebDriverWait(driver, context.getTimeout())).until(new ExpectedCondition<WebElement>() {
            public WebElement apply(WebDriver d) {
                return d.findElement(By.cssSelector(action.getSelector().getValue()));
            }
        });

        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();

        if (element.getTagName().equalsIgnoreCase(OPTION)) {
            element.click();
        } else {
            Select select = new Select(element);
            select.selectByValue(action.getValue().getValue());
        }

    }

}
