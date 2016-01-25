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
import website.automate.jwebrobot.models.scenario.actions.IfUnlessAction;
import website.automate.jwebrobot.models.scenario.actions.criteria.IfCriterion;
import website.automate.jwebrobot.models.scenario.actions.criteria.UnlessCriterion;

@RunWith(MockitoJUnitRunner.class)
public class IfUnlessActionExecutorTest {

    @Mock private ExpressionEvaluator expressionEvaluator;
    @Mock private IfUnlessAction action;
    @Mock private IfCriterion ifCriterion;
    @Mock private UnlessCriterion unlessCriterion;
    @Mock private TestActionExecution execution;
    @Mock private ScenarioExecutionContext context;
    
    private static final String 
        TRUE_VALUE = "true",
        FALSE_VALUE = "false";
    
    private TestIfUnlessActionExecutor executor;
    
    @SuppressWarnings("unchecked")
    @Before
    public void init(){
        executor = new TestIfUnlessActionExecutor(expressionEvaluator, execution);
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
        when(action.getIf()).thenReturn(ifCriterion);
        when(ifCriterion.getValue()).thenReturn(TRUE_VALUE);
        
        executor.execute(action, context);
        
        verify(execution).run();
    }
    
    @Test
    public void actionIsNotExecutedWhenOnlyIfIsSetAndEvaluatesFalse(){
        when(action.getIf()).thenReturn(ifCriterion);
        when(ifCriterion.getValue()).thenReturn(FALSE_VALUE);
        
        executor.execute(action, context);
        
        verify(execution, never()).run();
    }
    
    @Test
    public void actionIsExecutedWhenOnlyUnlessIsSetAndEvaluatesFalse(){
        when(action.getUnless()).thenReturn(unlessCriterion);
        when(unlessCriterion.getValue()).thenReturn(FALSE_VALUE);
        
        executor.execute(action, context);
        
        verify(execution).run();
    }
    
    @Test
    public void actionIsNotExecutedWhenOnlyUnlessIsSetAndEvaluatesTrue(){
        when(action.getUnless()).thenReturn(unlessCriterion);
        when(unlessCriterion.getValue()).thenReturn(TRUE_VALUE);
        
        executor.execute(action, context);
        
        verify(execution, never()).run();
    }
    
    @Test
    public void actionIsExecutedWhenIfIsSetTrueAndUnlessIsSetFalse(){
        when(action.getIf()).thenReturn(ifCriterion);
        when(ifCriterion.getValue()).thenReturn(TRUE_VALUE);
        when(action.getUnless()).thenReturn(unlessCriterion);
        when(unlessCriterion.getValue()).thenReturn(FALSE_VALUE);
        
        executor.execute(action, context);
        
        verify(execution).run();
    }
    
    @Test
    public void actionIsNotExecutedWhenIfIsSetFalseOrUnlessIsSetTrue(){
        when(action.getIf()).thenReturn(ifCriterion);
        when(ifCriterion.getValue()).thenReturn(FALSE_VALUE);
        when(action.getUnless()).thenReturn(unlessCriterion);
        when(unlessCriterion.getValue()).thenReturn(FALSE_VALUE);
        
        executor.execute(action, context);
        
        verify(execution, never()).run();
    }
    
    static final class TestIfUnlessActionExecutor extends IfUnlessActionExecutor<IfUnlessAction> {

        private TestActionExecution execution;
        
        public TestIfUnlessActionExecutor(
                ExpressionEvaluator expressionEvaluator,
                TestActionExecution execution) {
            super(expressionEvaluator);
            this.execution = execution;
        }

        @Override
        public Class<IfUnlessAction> getActionType() {
            return IfUnlessAction.class;
        }

        @Override
        public void safeExecute(IfUnlessAction action,
                ScenarioExecutionContext context) {
            execution.run();
        }
    }
    
    static interface TestActionExecution {
        void run();
    }
}
