package website.automate.jwebrobot.executor.action;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.exceptions.ExceptionTranslator;
import website.automate.jwebrobot.executor.filter.ElementFilterChain;
import website.automate.jwebrobot.expression.ConditionalExpressionEvaluator;
import website.automate.jwebrobot.expression.ExpressionEvaluator;
import website.automate.jwebrobot.listener.ExecutionEventListeners;
import website.automate.waml.io.model.action.EnterAction;
import website.automate.waml.io.model.criteria.EnterCriteria;

@Service
public class EnterActionExecutor extends FilterActionExecutor<EnterAction> {

	@Autowired
    public EnterActionExecutor(ExpressionEvaluator expressionEvaluator,
            ExecutionEventListeners listener,
            ElementFilterChain elementFilterChain,
            ConditionalExpressionEvaluator conditionalExpressionEvaluator,
            ExceptionTranslator exceptionTranslator) {
        super(expressionEvaluator, listener,
                elementFilterChain,
                conditionalExpressionEvaluator,
                exceptionTranslator);
    }

    @Override
    public void perform(final EnterAction action, final ScenarioExecutionContext context) {
        WebDriver driver = context.getDriver();
        EnterCriteria criteria = action.getEnter();

        final WebElement element;

        boolean hasFilterCriteria = criteria.hasFilterCriteria();
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
        if (Boolean.parseBoolean(criteria.getClear())) {
            element.clear();
        }

        /**
         * See {@link org.openqa.selenium.Keys} to send keys.
         */
        element.sendKeys(criteria.getInput().toString());

    }

    @Override
    public Class<EnterAction> getSupportedType() {
        return EnterAction.class;
    }
}
