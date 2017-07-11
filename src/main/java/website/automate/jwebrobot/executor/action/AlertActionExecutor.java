package website.automate.jwebrobot.executor.action;

import com.google.inject.Inject;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.exceptions.BooleanExpectedException;
import website.automate.jwebrobot.exceptions.ExceptionTranslator;
import website.automate.jwebrobot.expression.ConditionalExpressionEvaluator;
import website.automate.jwebrobot.expression.ExpressionEvaluator;
import website.automate.jwebrobot.listener.ExecutionEventListeners;
import website.automate.jwebrobot.mapper.BooleanMapper;
import website.automate.waml.io.model.action.AlertAction;

public class AlertActionExecutor extends ConditionalActionExecutor<AlertAction> {

    @Inject
    public AlertActionExecutor(ExpressionEvaluator expressionEvaluator,
                              ExecutionEventListeners listener,
                              ConditionalExpressionEvaluator conditionalExpressionEvaluator,
                              ExceptionTranslator exceptionTranslator) {
        super(expressionEvaluator, listener,
            conditionalExpressionEvaluator,
            exceptionTranslator);
    }

    @Override
    public void perform(final AlertAction action, final ScenarioExecutionContext context) {
        WebDriver driver = context.getDriver();

        // Wait for the alert
        WebDriverWait wait = new WebDriverWait(driver, getActionTimeout(action, context));
        wait.until(ExpectedConditions.alertIsPresent());
        Alert alert = driver.switchTo().alert();

        // Validate text
        String expectedAlertText = action.getText();
        if (expectedAlertText != null && !expectedAlertText.equals(alert.getText())) {
            // TODO throw proper exception
            throw new WebDriverException();
        }

        // Enter text (in the prompt)
        String promptInput = action.getInput();
        if (promptInput != null ) {
            alert.sendKeys(promptInput);
        }

        String confirmValue = action.getConfirm();
        try {
            if (BooleanMapper.isTrue(confirmValue)) {
                alert.accept();
            } else {
                alert.dismiss();
            }
        } catch (BooleanExpectedException e) {
            throw new BooleanExpectedException(action.getClass(), confirmValue);
        }
    }

    @Override
    public Class<AlertAction> getSupportedType() {
        return AlertAction.class;
    }

}
