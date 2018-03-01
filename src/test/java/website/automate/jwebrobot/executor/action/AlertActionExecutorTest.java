package website.automate.jwebrobot.executor.action;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.exceptions.AlertTextMismatchException;
import website.automate.jwebrobot.exceptions.BooleanExpectedException;
import website.automate.waml.io.model.action.AlertAction;
import website.automate.waml.io.model.criteria.AlertCriteria;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AlertActionExecutorTest {

    private static final java.lang.String VALUE_CONFIRM = "yes";
    private static final java.lang.String VALUE_TEXT = "text";
    private static final java.lang.String VALUE_INPUT = "input";

    @InjectMocks
    private AlertActionExecutor alertActionExecutor;

    @Mock
    private AlertAction action;
    
    @Mock
    private AlertCriteria criteria;

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private ScenarioExecutionContext context;

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private WebDriver driver;

    @Mock
    private Alert alert;

    @Before
    public void setUpAlert() {
        when(context.getDriver()).thenReturn(driver);
        when(driver.switchTo().alert()).thenReturn(alert);
        when(context.getGlobalContext().getOptions().getTimeout()).thenReturn(10L);
        when(alert.getText()).thenReturn(VALUE_TEXT);

        when(action.getAlert()).thenReturn(criteria);
        when(criteria.getConfirm()).thenReturn(VALUE_CONFIRM);
        when(criteria.getText()).thenReturn(VALUE_TEXT);
        when(criteria.getInput()).thenReturn(VALUE_INPUT);
    }

    @Test
    public void shouldConfirmAlert() {
        alertActionExecutor.perform(action, context);

        verify(alert).accept();
    }

    @Test
    public void shouldDismissAlert() {
        when(criteria.getConfirm()).thenReturn("no");

        alertActionExecutor.perform(action, context);

        verify(alert).dismiss();
    }

    @Test
    public void shouldEnterText() {
        alertActionExecutor.perform(action, context);

        verify(alert).sendKeys(VALUE_INPUT);
        verify(alert).accept();
    }


    @Test(expected = AlertTextMismatchException.class)
    public void shouldVerifyAlertText() {
        when(criteria.getText()).thenReturn(UUID.randomUUID().toString());

        alertActionExecutor.perform(action, context);
    }

    @Test(expected = BooleanExpectedException.class)
    public void shouldClaimAboutUnknownConfirmCriteria() {
        when(criteria.getConfirm()).thenReturn(UUID.randomUUID().toString());

        alertActionExecutor.perform(action, context);
    }

    @Test
    public void shouldReturnAlertActionType() {
        Class<?> actualType = alertActionExecutor.getSupportedType();

        assertEquals(AlertAction.class, actualType);
    }
}
