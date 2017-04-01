package website.automate.jwebrobot.executor.action;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.exceptions.ExceptionTranslator;
import website.automate.jwebrobot.executor.filter.ElementFilterChain;
import website.automate.jwebrobot.expression.ConditionalExpressionEvaluator;
import website.automate.jwebrobot.expression.ExpressionEvaluator;
import website.automate.jwebrobot.listener.ExecutionEventListeners;
import website.automate.waml.io.model.CriterionType;
import website.automate.waml.io.model.action.StoreAction;

@RunWith(MockitoJUnitRunner.class)
public class StoreActionExecutorTest {

    @Mock private ExpressionEvaluator expressionEvaluator;
    @Mock private StoreAction action;
    @Mock private ScenarioExecutionContext context;
    @Mock private Map<String, Object> memory;
    @Mock private ExecutionEventListeners listener;
    @Mock private ConditionalExpressionEvaluator conditionalExpressionEvaluator;
    @Mock private ExceptionTranslator translator;
    @Mock private ElementFilterChain elementFilterChain;
    
    private static final CriterionType CRITERIA_TYPE = CriterionType.SELECTOR;
    
    private static final String EXPRESSION = "expression";
    
    private StoreActionExecutor executor;
    
    @SuppressWarnings("unchecked")
    @Before
    public void init(){
        executor = new StoreActionExecutor(expressionEvaluator, listener, elementFilterChain,
            conditionalExpressionEvaluator, translator);
        when(conditionalExpressionEvaluator.isExecutable(action, context)).thenReturn(true);
        when(action.getFacts()).thenReturn(Collections.singletonMap(CRITERIA_TYPE.getName(), EXPRESSION));
        when(context.getMemory()).thenReturn(memory);
        when(expressionEvaluator.evaluate(Mockito.eq(EXPRESSION), Mockito.anyMap())).thenReturn(EXPRESSION);
        when(action.getTimeout()).thenReturn("1");
    }
    
    @Test
    public void criteriaShouldBeStoredInMemory(){
        executor.execute(action, context);
        
        verify(memory).put(CRITERIA_TYPE.getName(), EXPRESSION);
    }
}
