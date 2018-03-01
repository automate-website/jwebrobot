package website.automate.jwebrobot.executor.action;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.exceptions.ExceptionTranslator;
import website.automate.jwebrobot.expression.ConditionalExpressionEvaluator;
import website.automate.jwebrobot.expression.ExpressionEvaluator;
import website.automate.jwebrobot.listener.ExecutionEventListeners;
import website.automate.waml.io.model.action.DefineAction;
import website.automate.waml.io.model.criteria.DefineCriteria;
import java.util.Map;
import static java.util.Collections.singletonMap;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class StoreActionExecutorTest {

    @Mock
    private ExpressionEvaluator expressionEvaluator;
    @Mock
    private DefineAction action;
    @Mock
    private DefineCriteria criteria;
    @Mock
    private ScenarioExecutionContext context;
    @Mock
    private Map<String, Object> memory;
    @Mock
    private ExecutionEventListeners listener;
    @Mock
    private ConditionalExpressionEvaluator conditionalExpressionEvaluator;
    @Mock
    private ExceptionTranslator translator;

    private static final String VARIABLE_NAME = "foo";

    private static final String EXPRESSION = "expression";

    private StoreActionExecutor executor;

    @SuppressWarnings("unchecked")
    @Before
    public void init() {
        executor = new StoreActionExecutor(expressionEvaluator, listener,
                conditionalExpressionEvaluator, translator);
        when(action.getDefine()).thenReturn(criteria);
        when(conditionalExpressionEvaluator.isExecutable(action, context)).thenReturn(true);
        when(criteria.getFacts()).thenReturn(singletonMap(VARIABLE_NAME, EXPRESSION));
        when(context.getMemory()).thenReturn(memory);
        when(expressionEvaluator.evaluate(Mockito.eq(EXPRESSION), Mockito.anyMap()))
                .thenReturn(EXPRESSION);
    }

    @Test
    public void criteriaShouldBeStoredInMemory() {
        executor.execute(action, context);

        verify(memory).put(VARIABLE_NAME, EXPRESSION);
    }
}
