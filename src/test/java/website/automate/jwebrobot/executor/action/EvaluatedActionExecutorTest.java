package website.automate.jwebrobot.executor.action;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.util.Collections;

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
public class EvaluatedActionExecutorTest {

    @Mock private ExpressionEvaluator expressionEvaluator;
    @Mock private Action action;
    @Mock private CriteriaValue criteraValue;
    @Mock private ScenarioExecutionContext context;
    @Mock private ExecutionEventListeners listener;
    @Mock private ConditionalExpressionEvaluator conditionalExpressionEvaluator;
    
    private static final ActionType ACTION_TYPE = ActionType.CLICK;
    
    private static final CriteriaType CRITERIA_TYPE = CriteriaType.SELECTOR;
    
    private static final String 
        EXPRESSION = "expression",
        EVALUATED_EXPRESSION = "evaluated-expression";
    
    private EvaluatedActionExecutor executor;
    
    @SuppressWarnings("unchecked")
    @Before
    public void init(){
        executor = new TestEvaluatedActionExecutor(expressionEvaluator, listener, conditionalExpressionEvaluator);
        when(criteraValue.asString()).thenReturn(EXPRESSION);
        when(action.getType()).thenReturn(ACTION_TYPE);
        when(action.getCriteriaValueMap()).thenReturn(Collections.singletonMap(CRITERIA_TYPE.getName(), criteraValue));
        when(expressionEvaluator.evaluate(Mockito.eq(EXPRESSION), Mockito.anyMap())).thenReturn(EVALUATED_EXPRESSION);
    }
    
    @Test
    public void criteriaValueExpressionsAreEvaluated(){
        Action processedAction = executor.preprocess(action, context);
        
        assertThat(processedAction.getType(), is(action.getType()));
        CriteriaValue processedCriteriaValue = processedAction.getCriteria(CRITERIA_TYPE);
        assertThat(processedCriteriaValue.asString(), is(EVALUATED_EXPRESSION));
    }
    
    public static class TestEvaluatedActionExecutor extends EvaluatedActionExecutor {

        public TestEvaluatedActionExecutor(
                ExpressionEvaluator expressionEvaluator,
                ExecutionEventListeners listener,
                ConditionalExpressionEvaluator conditionalExpressionEvaluator) {
            super(expressionEvaluator, listener,
                    conditionalExpressionEvaluator);
        }

        @Override
        public ActionType getActionType() {
            return null;
        }

        @Override
        public void perform(Action action, ScenarioExecutionContext context) {
        }
        
    }
}
