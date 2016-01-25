package website.automate.jwebrobot.executor.action;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.inject.Inject;

import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.expression.ExpressionEvaluator;
import website.automate.jwebrobot.models.scenario.actions.EnsureAction;

public class EnsureActionExecutor extends IfUnlessActionExecutor<EnsureAction> {

    @Inject
    public EnsureActionExecutor(ExpressionEvaluator expressionEvaluator) {
        super(expressionEvaluator);
    }

    @Override
    public Class<EnsureAction> getActionType() {
        return EnsureAction.class;
    }

    @Override
    public void safeExecute(final EnsureAction action, ScenarioExecutionContext context) {
        WebDriver driver = context.getDriver();

        (new WebDriverWait(driver, context.getTimeout())).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                return d.findElement(By.cssSelector(action.getSelector().getValue())).isDisplayed();
            }
        });
    }

}
