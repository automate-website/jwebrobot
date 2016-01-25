package website.automate.jwebrobot.executor.action;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.inject.Inject;

import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.expression.ExpressionEvaluator;
import website.automate.jwebrobot.models.scenario.actions.MoveAction;

public class MoveActionExecutor extends IfUnlessActionExecutor<MoveAction> {

    @Inject
    public MoveActionExecutor(ExpressionEvaluator expressionEvaluator) {
        super(expressionEvaluator);
    }

    @Override
    public Class<MoveAction> getActionType() {
        return MoveAction.class;
    }

    @Override
    public void safeExecute(final MoveAction action, ScenarioExecutionContext context) {
        WebDriver driver = context.getDriver();

        WebElement element = (new WebDriverWait(driver, context.getTimeout())).until(new ExpectedCondition<WebElement>() {
            public WebElement apply(WebDriver d) {
                return d.findElement(By.cssSelector(action.getSelector().getValue()));
            }
        });

        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();
    }

}
