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
import website.automate.jwebrobot.expression.ConditionalExpressionEvaluator;
import website.automate.jwebrobot.expression.ExpressionEvaluator;
import website.automate.jwebrobot.listener.ExecutionEventListeners;
import website.automate.jwebrobot.model.Action;
import website.automate.jwebrobot.model.ActionType;
import website.automate.jwebrobot.model.CriteriaType;
import website.automate.jwebrobot.model.CriteriaValue;

@RunWith(MockitoJUnitRunner.class)
public class StoreActionExecutorTest {

    @Mock private ExpressionEvaluator expressionEvaluator;
    @Mock private Action action;
    @Mock private CriteriaValue criteraValue;
    @Mock private ScenarioExecutionContext context;
    @Mock private Map<String, Object> memory;
    @Mock private ExecutionEventListeners listener;
    @Mock private ConditionalExpressionEvaluator conditionalExpressionEvaluator;
    
    private static final ActionType ACTION_TYPE = ActionType.CLICK;
    
    private static final CriteriaType CRITERIA_TYPE = CriteriaType.SELECTOR;
    
    private static final String 
        EXPRESSION = "expression",
        EVALUATED_EXPRESSION = "evaluated-expression";
    
    private StoreActionExecutor executor;
    
    @SuppressWarnings("unchecked")
    @Before
    public void init(){
        executor = new StoreActionExecutor(expressionEvaluator, listener, conditionalExpressionEvaluator);
        when(conditionalExpressionEvaluator.isExecutable(action, context)).thenReturn(true);
        when(criteraValue.asString()).thenReturn(EXPRESSION);
        when(action.getType()).thenReturn(ACTION_TYPE);
        when(action.getCriteriaValueMap()).thenReturn(Collections.singletonMap(CRITERIA_TYPE.getName(), criteraValue));
        when(context.getMemory()).thenReturn(memory);
        when(expressionEvaluator.evaluate(Mockito.eq(EXPRESSION), Mockito.anyMap())).thenReturn(EVALUATED_EXPRESSION);
    }
    
    @Test
    public void criteriaShouldBeStoredInMemory(){
        executor.execute(action, context);
        
        verify(memory).put(CRITERIA_TYPE.getName(), EVALUATED_EXPRESSION);
    }
}
