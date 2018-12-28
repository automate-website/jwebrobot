package website.automate.jwebrobot.executor.action;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.exceptions.AlertTextMismatchException;
import website.automate.jwebrobot.exceptions.BooleanExpectedException;
import website.automate.jwebrobot.executor.ActionExecutorUtils;
import website.automate.jwebrobot.executor.ActionResult;
import website.automate.jwebrobot.executor.TimeoutResolver;
import website.automate.jwebrobot.expression.ConditionalExpressionEvaluator;
import website.automate.waml.io.model.main.action.AlertAction;
import website.automate.waml.io.model.main.criteria.AlertCriteria;

import static java.util.UUID.randomUUID;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
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

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private ScenarioExecutionContext context;

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private WebDriver driver;

    @Mock
    private Alert alert;

    @Mock
    private AlertCriteria alertCriteria;

    @Mock
    private ActionExecutorUtils utils;

    @Mock
    private TimeoutResolver timeoutResolver;

    @Mock
    private ConditionalExpressionEvaluator expressionEvaluator;

    @Mock
    private ActionEvaluator actionEvaluator;

    @Before
    public void setUpAlert() {
        when(context.getDriver()).thenReturn(driver);
        when(driver.switchTo().alert()).thenReturn(alert);
        when(context.getGlobalContext().getOptions().getTimeout()).thenReturn(10L);
        when(alert.getText()).thenReturn(VALUE_TEXT);
        when(utils.getTimeoutResolver()).thenReturn(timeoutResolver);
        when(utils.getConditionalExpressionEvaluator()).thenReturn(expressionEvaluator);
        when(expressionEvaluator.isExecutable(action, context)).thenReturn(true);
        when(timeoutResolver.resolve(action, context)).thenReturn(10L);
        when(action.getAlert()).thenReturn(alertCriteria);
        when(utils.getActionEvaluator()).thenReturn(actionEvaluator);
        when(actionEvaluator.evaluate(action, context)).thenReturn(action);

        when(alertCriteria.getConfirm()).thenReturn(VALUE_CONFIRM);
        when(alertCriteria.getText()).thenReturn(VALUE_TEXT);
        when(alertCriteria.getInput()).thenReturn(VALUE_INPUT);
    }

    @Test
    public void shouldConfirmAlert() {
        alertActionExecutor.perform(action, context, utils);

        verify(alert).accept();
    }

    @Test
    public void shouldDismissAlert() {
        when(alertCriteria.getConfirm()).thenReturn("no");

        alertActionExecutor.perform(action, context, utils);

        verify(alert).dismiss();
    }

    @Test
    public void shouldEnterText() {
        alertActionExecutor.perform(action, context, utils);

        verify(alert).sendKeys(VALUE_INPUT);
        verify(alert).accept();
    }


    @Test
    public void shouldVerifyAlertText() {
        when(alertCriteria.getText()).thenReturn(randomUUID().toString());

        ActionResult result = alertActionExecutor.perform(action, context, utils);

        assertTrue(result.getError() instanceof AlertTextMismatchException);
    }

    @Test
    public void shouldClaimAboutUnknownConfirmCriteria() {
        when(alertCriteria.getConfirm()).thenReturn(randomUUID().toString());

        ActionResult result = alertActionExecutor.perform(action, context, utils);

        assertTrue(result.getError() instanceof BooleanExpectedException);
    }

    @Test
    public void shouldReturnAlertActionType() {
        Class<?> actualType = alertActionExecutor.getSupportedType();

        assertEquals(AlertAction.class, actualType);
    }
}
