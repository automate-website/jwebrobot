package website.automate.jwebrobot.executor.action;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
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
import website.automate.waml.io.model.action.MoveAction;

@Service
public class MoveActionExecutor extends FilterActionExecutor<MoveAction> {

    @Autowired
    public MoveActionExecutor(ExpressionEvaluator expressionEvaluator,
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
    public void perform(final MoveAction action, final ScenarioExecutionContext context) {
        WebDriver driver = context.getDriver();

        WebElement element = (new WebDriverWait(driver, getActionTimeout(action, context))).until(new ExpectedCondition<WebElement>() {
            public WebElement apply(WebDriver d) {
                return filter(context, action);
            }
        });

        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();
    }

    @Override
    public Class<MoveAction> getSupportedType() {
       return MoveAction.class;
    }

}
