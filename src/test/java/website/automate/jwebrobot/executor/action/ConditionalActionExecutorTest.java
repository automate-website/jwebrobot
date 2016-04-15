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
import website.automate.waml.io.model.action.ConditionalAction;

@RunWith(MockitoJUnitRunner.class)
public class ConditionalActionExecutorTest {

    @Mock private ExpressionEvaluator expressionEvaluator;
    @Mock private ConditionalAction action;
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
    
    static final class TestConditionalActionExecutor extends ConditionalActionExecutor<ConditionalAction> {

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
        public void perform(ConditionalAction action, ScenarioExecutionContext context) {
            execution.run();
        }

        @Override
        public Class<ConditionalAction> getSupportedType() {
            return ConditionalAction.class;
        }
    }
    
    static interface TestActionExecution {
        void run();
    }
}
