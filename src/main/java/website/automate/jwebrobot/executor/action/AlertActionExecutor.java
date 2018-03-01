package website.automate.jwebrobot.executor.action;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.exceptions.AlertTextMismatchException;
import website.automate.jwebrobot.exceptions.BooleanExpectedException;
import website.automate.jwebrobot.exceptions.ExceptionTranslator;
import website.automate.jwebrobot.expression.ConditionalExpressionEvaluator;
import website.automate.jwebrobot.expression.ExpressionEvaluator;
import website.automate.jwebrobot.listener.ExecutionEventListeners;
import website.automate.jwebrobot.mapper.BooleanMapper;
import website.automate.waml.io.model.action.AlertAction;
import website.automate.waml.io.model.criteria.AlertCriteria;

@Service
public class AlertActionExecutor extends ConditionalActionExecutor<AlertAction> {

    @Autowired
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
        AlertCriteria criteria = action.getAlert();

        // Validate text
        String expectedAlertText = criteria.getText();
        String alertText = alert.getText();
        if (expectedAlertText != null && !expectedAlertText.equals(alertText)) {
            throw new AlertTextMismatchException(action.getClass(), expectedAlertText, alertText);
        }

        // Enter text (in the prompt)
        String promptInput = criteria.getInput();
        if (promptInput != null ) {
            alert.sendKeys(promptInput);
        }

        String confirmValue = criteria.getConfirm();
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
