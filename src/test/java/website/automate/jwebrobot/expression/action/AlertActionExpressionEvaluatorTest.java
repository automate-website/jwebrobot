package website.automate.jwebrobot.expression.action;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.expression.ExpressionEvaluator;
import website.automate.waml.io.model.action.AlertAction;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AlertActionExpressionEvaluatorTest {

    public static final String VALUE_CONFIRM = UUID.randomUUID().toString();
    public static final String VALUE_TEXT = UUID.randomUUID().toString();
    public static final String VALUE_INPUT = UUID.randomUUID().toString();

    public static final String VALUE_CONFIRM_PROCESSED = UUID.randomUUID().toString();
    public static final String VALUE_TEXT_PROCESSED = UUID.randomUUID().toString();
    public static final String VALUE_INPUT_PROCESSED = UUID.randomUUID().toString();

    @InjectMocks
    private AlertActionExpressionEvaluator actionExpressionEvaluator;

    @Mock
    private ExpressionEvaluator expressionEvaluator;

    @Mock
    private ScenarioExecutionContext context;

    @Mock
    private AlertAction action;

    @Test
    public void shouldEvaluateCriteria() {
        when(action.getConfirm()).thenReturn(VALUE_CONFIRM);
        when(action.getText()).thenReturn(VALUE_TEXT);
        when(action.getInput()).thenReturn(VALUE_INPUT);

        when(expressionEvaluator.evaluate(VALUE_CONFIRM, context.getTotalMemory())).thenReturn(VALUE_CONFIRM_PROCESSED);
        when(expressionEvaluator.evaluate(VALUE_TEXT, context.getTotalMemory())).thenReturn(VALUE_TEXT_PROCESSED);
        when(expressionEvaluator.evaluate(VALUE_INPUT, context.getTotalMemory())).thenReturn(VALUE_INPUT_PROCESSED);

        actionExpressionEvaluator.evaluate(action, context);

        verify(action).setConfirm(VALUE_CONFIRM_PROCESSED);
        verify(action).setText(VALUE_TEXT_PROCESSED);
        verify(action).setInput(VALUE_INPUT_PROCESSED);
    }

    @Test
    public void shouldReturnAlertActionType() {
        Class<?> actualType = actionExpressionEvaluator.getSupportedType();

        assertEquals(AlertAction.class, actualType);
    }
}
