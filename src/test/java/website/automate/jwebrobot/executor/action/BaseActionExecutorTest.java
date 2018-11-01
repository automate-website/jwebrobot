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
import website.automate.jwebrobot.executor.StepExecutionUtils;
import website.automate.jwebrobot.executor.ExecutorOptions;
import website.automate.jwebrobot.listener.ExecutionEventListeners;
import website.automate.waml.io.model.Scenario;
import website.automate.waml.io.model.action.TimeLimitedAction;

@RunWith(MockitoJUnitRunner.class)
public class BaseActionExecutorTest {

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
    @Mock private StepExecutionUtils stepExecutionUtils;
    
    private BaseActionExecutor<TimeLimitedAction> executor;
    
    @Before
    public void init(){
        executor = new TestBaseActionExecutor(listener, translator);
        when(context.getScenario()).thenReturn(scenario);
        when(context.getGlobalContext()).thenReturn(globalContext);
        when(globalContext.getOptions()).thenReturn(options);
    }
    
    @Test
    public void listenerIsCalledBeforeActionExecution(){
        executor.execute(action, context, stepExecutionUtils);
        
        verify(listener).beforeAction(context, action);
    }
    
    @Test
    public void listenerIsCalledAfterActionExecution(){
        executor.execute(action, context, stepExecutionUtils);
        
        verify(listener).afterAction(context, action);
    }
    
    @Test
    public void listenerIsCalledAfterExecutionError(){
        executor = new ExceptionalTestBaseActionExecutor(listener, exception, translator);
        exceptionExpectation.expect(RuntimeException.class);
        
        executor.execute(action, context, stepExecutionUtils);
        
        verify(listener).errorAction(context, action, exception);
    }
    
    @Test
    public void defaultTimeoutChoosenIfNothingSet(){
    	when(options.getTimeout()).thenReturn(null);
    	
    	Long actualTimeout = executor.getActionTimeout(action, context);
    	
    	assertThat(actualTimeout, is(1L));
    }
    
    @Test
    public void actionTimeoutChoosenIfSet(){
        when(options.getTimeout()).thenReturn(null);
        when(action.getTimeout()).thenReturn(ACTION_TIMEOUT);
        
        Long actualTimeout = executor.getActionTimeout(action, context);
        
        assertThat(actualTimeout, is(Long.parseLong(ACTION_TIMEOUT)));
    }
    
    @Test
    public void scenarioTimeoutChoosenIfActionTimeoutIsNotSet(){
        when(scenario.getTimeout()).thenReturn(SCENARIO_TIMEOUT);
        when(options.getTimeout()).thenReturn(null);
        
        Long actualTimeout = executor.getActionTimeout(action, context);
        
        assertThat(actualTimeout, is(Long.parseLong(SCENARIO_TIMEOUT)));
    }
    
    @Test
    public void globalTimeoutChoosenIfSet(){
        when(options.getTimeout()).thenReturn(GLOBAL_TIMEOUT);
        
        Long actualTimeout = executor.getActionTimeout(action, context);
        
        assertThat(actualTimeout, is(GLOBAL_TIMEOUT));
    }
    
    private static class ExceptionalTestBaseActionExecutor extends BaseActionExecutor<TimeLimitedAction> {

        private RuntimeException exception;
        
        public ExceptionalTestBaseActionExecutor(
                ExecutionEventListeners listener,
                RuntimeException exception,
                ExceptionTranslator translator) {
            super(listener, translator);
            this.exception = exception;
        }

        @Override
        public void perform(TimeLimitedAction action, ScenarioExecutionContext context) {
            throw exception;
        }

        @Override
        public Class<TimeLimitedAction> getSupportedType() {
            return TimeLimitedAction.class;
        }
        
    }
    
    private static class TestBaseActionExecutor extends BaseActionExecutor<TimeLimitedAction> {

        public TestBaseActionExecutor(ExecutionEventListeners listener,
        		ExceptionTranslator translator) {
            super(listener, translator);
        }

        @Override
        public void perform(TimeLimitedAction action, ScenarioExecutionContext context) {
        }

        @Override
        public Class<TimeLimitedAction> getSupportedType() {
            return TimeLimitedAction.class;
        }
        
    }
}
