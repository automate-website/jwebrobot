package website.automate.jwebrobot.executor.action;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.expression.ConditionalExpressionEvaluator;
import website.automate.jwebrobot.expression.ExpressionEvaluator;
import website.automate.jwebrobot.listener.ExecutionEventListeners;
import website.automate.jwebrobot.model.Action;
import website.automate.jwebrobot.model.ActionType;
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
    @Mock private ConditionalExpressionEvaluator conditionalExpressionEvaluator;
    
    private TestConditionalActionExecutor executor;
    
    @Before
    public void init(){
        executor = new TestConditionalActionExecutor(expressionEvaluator, execution, listener, conditionalExpressionEvaluator);
    }
    
    @Test
    public void actionIsExecutedWhenIdentifiedAsExecutable(){
        when(conditionalExpressionEvaluator.isExecutable(action, context)).thenReturn(true);
        
        executor.execute(action, context);
        
        verify(execution).run();
    }
    
    @Test
    public void actionIsNotExecutedWhenIdentifiedAsExecutable(){
        when(conditionalExpressionEvaluator.isExecutable(action, context)).thenReturn(false);
        
        executor.execute(action, context);
        
        verify(execution, never()).run();
    }
    
    static final class TestConditionalActionExecutor extends ConditionalActionExecutor {

        private TestActionExecution execution;
        
        public TestConditionalActionExecutor(
                ExpressionEvaluator expressionEvaluator,
                TestActionExecution execution,
                ExecutionEventListeners listener,
                ConditionalExpressionEvaluator conditionalExpressionEvaluator) {
            super(expressionEvaluator, listener, conditionalExpressionEvaluator);
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
