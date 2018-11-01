package website.automate.jwebrobot.executor.action;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import org.mockito.junit.MockitoJUnitRunner;
import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.exceptions.ExceptionTranslator;
import website.automate.jwebrobot.executor.StepExecutionUtils;
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
    @Mock private ExceptionTranslator translator;
    @Mock private StepExecutionUtils stepExecutionUtils;
    
    private TestConditionalActionExecutor executor;
    
    @Before
    public void init(){
        executor = new TestConditionalActionExecutor(expressionEvaluator, execution,
        		listener, conditionalExpressionEvaluator,
        		translator);
    }
    
    @Test
    public void actionIsExecutedWhenIdentifiedAsExecutable(){
        when(conditionalExpressionEvaluator.isExecutable(action, context)).thenReturn(true);
        
        executor.execute(action, context, stepExecutionUtils);
        
        verify(execution).run();
    }
    
    @Test
    public void actionIsNotExecutedWhenIdentifiedAsExecutable(){
        when(conditionalExpressionEvaluator.isExecutable(action, context)).thenReturn(false);
        
        executor.execute(action, context, stepExecutionUtils);
        
        verify(execution, never()).run();
    }
    
    static final class TestConditionalActionExecutor extends ConditionalActionExecutor<ConditionalAction> {

        private TestActionExecution execution;
        
        public TestConditionalActionExecutor(
                ExpressionEvaluator expressionEvaluator,
                TestActionExecution execution,
                ExecutionEventListeners listener,
                ConditionalExpressionEvaluator conditionalExpressionEvaluator,
                ExceptionTranslator translator) {
            super(expressionEvaluator, listener, conditionalExpressionEvaluator, translator);
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
