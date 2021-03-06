/*
package website.automate.jwebrobot.executor.action;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import org.mockito.junit.MockitoJUnitRunner;
import website.automate.jwebrobot.context.GlobalExecutionContext;
import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.exceptions.ExceptionTranslator;
import website.automate.jwebrobot.executor.ActionExecutorUtils;
import website.automate.jwebrobot.executor.ExecutorOptions;
import website.automate.jwebrobot.listener.ExecutionEventListeners;
import website.automate.waml.io.model.Scenario;
import website.automate.waml.io.model.action.Action;
import website.automate.waml.io.model.action.TimeLimitedAction;

@RunWith(MockitoJUnitRunner.class)
public class StepExecutorTest {

    private static final String
        SCENARIO_TIMEOUT = "3";
    
    private static final Long GLOBAL_TIMEOUT = 1L;
    private static final String ACTION_TIMEOUT = "2";
    
    @Rule
    public final ExpectedException exceptionExpectation = ExpectedException.none();
    
    @Mock private ExecutionEventListeners listener;
    @Mock private TimeLimitedAction action;
    @Mock private ScenarioExecutionContext context;
    @Mock private GlobalExecutionContext globalContext;
    @Mock private Scenario scenario;
    @Mock private ExecutorOptions options;
    @Mock private RuntimeException exception;
    @Mock private ExceptionTranslator translator;
    @Mock private ActionExecutorUtils actionExecutionUtils;
    
    private StepExecutor executor;
    
    @Before
    public void init(){
        executor = new TestStepExecutor(listener, translator);
        when(context.getScenarioByPath()).thenReturn(scenario);
        when(context.getGlobalContext()).thenReturn(globalContext);
        when(globalContext.getOptions()).thenReturn(options);
    }
    
    @Test
    public void listenerIsCalledBeforeActionExecution(){
        executor.execute(action, context, actionExecutionUtils);
        
        verify(listener).beforeAction(context, action);
    }
    
    @Test
    public void listenerIsCalledAfterActionExecution(){
        executor.execute(action, context, actionExecutionUtils);
        
        verify(listener).afterAction(context, action);
    }
    
    @Test
    public void listenerIsCalledAfterExecutionError(){
        executor = new ExceptionalTestStepExecutor(listener, exception, translator);
        exceptionExpectation.expect(RuntimeException.class);
        
        executor.execute(action, context, actionExecutionUtils);
        
        verify(listener).errorAction(context, action, exception);
    }
    
    private static class ExceptionalTestStepExecutor extends StepExecutor {

        private RuntimeException exception;

        public ExceptionalTestStepExecutor(RuntimeException exception) {
            super(null, null, null, null, null);
        }
        
        @Override
        public void execute(Action action, ScenarioExecutionContext context){
            throw exception;
        }
    }
    
    private static class TestStepExecutor extends StepExecutor {

        public TestStepExecutor(ExecutionEventListeners listener,
                                ExceptionTranslator translator) {
            super(null, null, null, null, null);
        }

        public void execute(TimeLimitedAction action, ScenarioExecutionContext context) {
        }

    }
}
*/
