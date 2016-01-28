package website.automate.jwebrobot.executor.action;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.inject.Inject;

import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.expression.ExpressionEvaluator;
import website.automate.jwebrobot.model.Action;
import website.automate.jwebrobot.model.ActionType;
import website.automate.jwebrobot.model.CriteriaType;

public class SelectActionExecutor extends EvaluatedActionExecutor {

    private static final String OPTION = "option";

    @Inject
    public SelectActionExecutor(ExpressionEvaluator expressionEvaluator) {
        super(expressionEvaluator);
    }
    
    @Override
    public ActionType getActionType() {
        return ActionType.SELECT;
    }

    @Override
    public void perform(final Action action, ScenarioExecutionContext context) {
        WebDriver driver = context.getDriver();

        WebElement element = (new WebDriverWait(driver, context.getTimeout())).until(new ExpectedCondition<WebElement>() {
            public WebElement apply(WebDriver d) {
                return d.findElement(By.cssSelector(action.getCriteriaOrDefault(CriteriaType.SELECTOR).asString()));
            }
        });

        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();

        if (element.getTagName().equalsIgnoreCase(OPTION)) {
            element.click();
        } else {
            Select select = new Select(element);
            select.selectByValue(action.getValue());
        }

    }

}
