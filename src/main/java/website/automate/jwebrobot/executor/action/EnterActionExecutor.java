package website.automate.jwebrobot.executor.action;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.inject.Inject;

import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.executor.filter.ElementFilterChain;
import website.automate.jwebrobot.expression.ConditionalExpressionEvaluator;
import website.automate.jwebrobot.expression.ExpressionEvaluator;
import website.automate.jwebrobot.listener.ExecutionEventListeners;
import website.automate.waml.io.model.action.EnterAction;

public class EnterActionExecutor extends FilterActionExecutor<EnterAction> {

    @Inject
    public EnterActionExecutor(ExpressionEvaluator expressionEvaluator,
            ExecutionEventListeners listener,
            ElementFilterChain elementFilterChain,
            ConditionalExpressionEvaluator conditionalExpressionEvaluator) {
        super(expressionEvaluator, listener,
                elementFilterChain,
                conditionalExpressionEvaluator);
    }

    @Override
    public void perform(final EnterAction action, final ScenarioExecutionContext context) {
        WebDriver driver = context.getDriver();

        final WebElement element;
        
        boolean hasFilterCriteria = action.hasFilterCriteria();
        if (hasFilterCriteria) {
            element = (new WebDriverWait(driver, getActionTimeout(action, context))).until(new ExpectedCondition<WebElement>() {
                public WebElement apply(WebDriver d) {
                    return filter(context, action);
                }
            });
        } else {
            element = driver.switchTo().activeElement();
        }

        // Make clear
        if (action.getClear() != null && action.getClear().toBoolean()) {
            element.clear();
        }

        /**
         * See {@link org.openqa.selenium.Keys} to send keys.
         */
        element.sendKeys(action.getInput().toString());

    }

    @Override
    public Class<EnterAction> getSupportedType() {
        return EnterAction.class;
    }
}
