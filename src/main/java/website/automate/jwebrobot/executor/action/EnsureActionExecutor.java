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
import website.automate.waml.io.model.action.EnsureAction;

@Service
public class EnsureActionExecutor extends FilterActionExecutor<EnsureAction> {

    @Autowired
    public EnsureActionExecutor(ExpressionEvaluator expressionEvaluator,
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
    public void perform(final EnsureAction action, final ScenarioExecutionContext context) {
        WebDriver driver = context.getDriver();

        final Boolean absent = Boolean.parseBoolean(action.getInvert());
        (new WebDriverWait(driver, getActionTimeout(action, context))).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                WebElement webElement = filter(context, action);
                if(webElement == null){
                    return absent;
                } else {
                    if(absent){
                        return Boolean.FALSE;
                    }
                    return webElement.isDisplayed();
                }
            }
        });
    }

    @Override
    public Class<EnsureAction> getSupportedType() {
        return EnsureAction.class;
    }
}
