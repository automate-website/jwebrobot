package website.automate.jwebrobot.executor.action;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;
import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.exceptions.AlertTextMismatchException;
import website.automate.jwebrobot.exceptions.BooleanExpectedException;
import website.automate.jwebrobot.executor.ActionExecutorUtils;
import website.automate.jwebrobot.executor.ActionExecutionResult;
import website.automate.jwebrobot.mapper.BooleanMapper;
import website.automate.waml.io.model.main.action.AlertAction;

@Service
public class AlertActionExecutor implements ActionExecutor<AlertAction> {

    @Override
    public ActionExecutionResult execute(final AlertAction action,
                                         final ScenarioExecutionContext context,
                                         final ActionExecutorUtils utils) {
        WebDriver driver = context.getDriver();

        // Wait for the alert
        WebDriverWait wait = new WebDriverWait(driver, utils.getTimeoutResolver().resolve(action, context));
        wait.until(ExpectedConditions.alertIsPresent());
        Alert alert = driver.switchTo().alert();

        // Validate text
        String expectedAlertText = action.getAlert().getText();
        String alertText = alert.getText();
        if (expectedAlertText != null && !expectedAlertText.equals(alertText)) {
            throw new AlertTextMismatchException(action.getClass(), expectedAlertText, alertText);
        }

        // Enter text (in the prompt)
        String promptInput = action.getAlert().getInput();
        if (promptInput != null ) {
            alert.sendKeys(promptInput);
        }

        String confirmValue = action.getAlert().getConfirm();
        try {
            if (BooleanMapper.isTrue(confirmValue)) {
                alert.accept();
            } else {
                alert.dismiss();
            }
        } catch (BooleanExpectedException e) {
            throw new BooleanExpectedException(action.getClass(), confirmValue);
        }

        return new ActionExecutionResult.ActionResultBuilder()
            .withCode(ActionExecutionResult.StatusCode.SUCCESS)
            .withFailed(false)
            .build();
    }

    @Override
    public Class<AlertAction> getSupportedType() {
        return AlertAction.class;
    }

}
