package website.automate.jwebrobot.executor.action;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.expression.ExpressionEvaluator;
import website.automate.jwebrobot.listener.ExecutionEventListeners;
import website.automate.jwebrobot.model.Action;
import website.automate.jwebrobot.model.ActionType;
import website.automate.jwebrobot.model.CriteriaType;
import website.automate.jwebrobot.model.CriteriaValue;

@RunWith(MockitoJUnitRunner.class)
public class ConditionalActionExecutorTest {

    @Mock private ExpressionEvaluator expressionEvaluator;
    @Mock private Action action;
    @Mock private CriteriaValue ifCriterion;
    @Mock private CriteriaValue unlessCriterion;
    @Mock private TestActionExecution execution;
    @Mock private ScenarioExecutionContext context;
    @Mock private ExecutionEventListeners listener;
    
    private static final String 
        TRUE_VALUE = "true",
        FALSE_VALUE = "false";
    
    private TestConditionalActionExecutor executor;
    
    @SuppressWarnings("unchecked")
    @Before
    public void init(){
        executor = new TestConditionalActionExecutor(expressionEvaluator, execution, listener);
        when(expressionEvaluator.evaluate(Mockito.eq(TRUE_VALUE), Mockito.anyMap())).thenReturn(true);
        when(expressionEvaluator.evaluate(Mockito.eq(FALSE_VALUE), Mockito.anyMap())).thenReturn(false);
    }
    
    @Test
    public void actionIsExecutedWhenNoConditionalsAreSet(){
        executor.execute(action, context);
        
        verify(execution).run();
    }
    
    @Test
    public void actionIsExecutedWhenOnlyIfIsSetAndEvaluatesTrue(){
        when(action.getCriteria(CriteriaType.IF)).thenReturn(ifCriterion);
        when(ifCriterion.asString()).thenReturn(TRUE_VALUE);
        
        executor.execute(action, context);
        
        verify(execution).run();
    }
    
    @Test
    public void actionIsNotExecutedWhenOnlyIfIsSetAndEvaluatesFalse(){
        when(action.getCriteria(CriteriaType.IF)).thenReturn(ifCriterion);
        when(ifCriterion.asString()).thenReturn(FALSE_VALUE);
        
        executor.execute(action, context);
        
        verify(execution, never()).run();
    }
    
    @Test
    public void actionIsExecutedWhenOnlyUnlessIsSetAndEvaluatesFalse(){
        when(action.getCriteria(CriteriaType.UNLESS)).thenReturn(unlessCriterion);
        when(unlessCriterion.asString()).thenReturn(FALSE_VALUE);
        
        executor.execute(action, context);
        
        verify(execution).run();
    }
    
    @Test
    public void actionIsNotExecutedWhenOnlyUnlessIsSetAndEvaluatesTrue(){
        when(action.getCriteria(CriteriaType.UNLESS)).thenReturn(unlessCriterion);
        when(unlessCriterion.asString()).thenReturn(TRUE_VALUE);
        
        executor.execute(action, context);
        
        verify(execution, never()).run();
    }
    
    @Test
    public void actionIsExecutedWhenIfIsSetTrueAndUnlessIsSetFalse(){
        when(action.getCriteria(CriteriaType.IF)).thenReturn(ifCriterion);
        when(ifCriterion.asString()).thenReturn(TRUE_VALUE);
        when(action.getCriteria(CriteriaType.UNLESS)).thenReturn(unlessCriterion);
        when(unlessCriterion.asString()).thenReturn(FALSE_VALUE);
        
        executor.execute(action, context);
        
        verify(execution).run();
    }
    
    @Test
    public void actionIsNotExecutedWhenIfIsSetFalseOrUnlessIsSetTrue(){
        when(action.getCriteria(CriteriaType.IF)).thenReturn(ifCriterion);
        when(ifCriterion.asString()).thenReturn(FALSE_VALUE);
        when(action.getCriteria(CriteriaType.UNLESS)).thenReturn(unlessCriterion);
        when(unlessCriterion.asString()).thenReturn(FALSE_VALUE);
        
        executor.execute(action, context);
        
        verify(execution, never()).run();
    }
    
    static final class TestConditionalActionExecutor extends ConditionalActionExecutor {

        private TestActionExecution execution;
        
        public TestConditionalActionExecutor(
                ExpressionEvaluator expressionEvaluator,
                TestActionExecution execution,
                ExecutionEventListeners listener) {
            super(expressionEvaluator, listener);
            this.execution = execution;
        }

        @Override
        public ActionType getActionType() {
            return null;
        }

        @Override
        public void perform(Action action, ScenarioExecutionContext context) {
            execution.run();
        }
    }
    
    static interface TestActionExecution {
        void run();
    }
}
