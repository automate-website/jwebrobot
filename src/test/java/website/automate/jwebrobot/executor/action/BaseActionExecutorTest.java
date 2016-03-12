package website.automate.jwebrobot.executor.action;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static website.automate.waml.io.model.CriterionValue.of;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import website.automate.jwebrobot.context.GlobalExecutionContext;
import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.executor.ExecutorOptions;
import website.automate.jwebrobot.listener.ExecutionEventListeners;
import website.automate.waml.io.model.CriterionValue;
import website.automate.waml.io.model.Scenario;
import website.automate.waml.io.model.action.TimeLimitedAction;

@RunWith(MockitoJUnitRunner.class)
public class BaseActionExecutorTest {

    private static final String
        SCENARIO_TIMEOUT = "3";
    
    private static final Long
        GLOBAL_TIMEOUT = 1L,
        ACTION_TIMEOUT = 2L;
    
    @Rule
    public final ExpectedException exceptionExpectation = ExpectedException.none();
    
    @Mock private ExecutionEventListeners listener;
    @Mock private TimeLimitedAction action;
    @Mock private ScenarioExecutionContext context;
    @Mock private GlobalExecutionContext globalContext;
    @Mock private Scenario scenario;
    @Mock private ExecutorOptions options;
    @Mock private CriterionValue actionTimeoutValue;
    @Mock private RuntimeException exception;
    
    private BaseActionExecutor<TimeLimitedAction> executor;
    
    @Before
    public void init(){
        executor = new TestBaseActionExecutor(listener);
        when(context.getScenario()).thenReturn(scenario);
        when(context.getGlobalContext()).thenReturn(globalContext);
        when(globalContext.getOptions()).thenReturn(options);
    }
    
    @Test
    public void listenerIsCalledBeforeActionExecution(){
        executor.execute(action, context);
        
        verify(listener).beforeAction(context, action);
    }
    
    @Test
    public void listenerIsCalledAfterActionExecution(){
        executor.execute(action, context);
        
        verify(listener).afterAction(context, action);
    }
    
    @Test
    public void listenerIsCalledAfterExecutionError(){
        executor = new ExceptionalTestBaseActionExecutor(listener, exception);
        exceptionExpectation.expect(RuntimeException.class);
        
        executor.execute(action, context);
        
        verify(listener).errorAction(context, action, exception);
    }
    
    @Test
    public void actionTimeoutChoosenIfSet(){
        when(scenario.getTimeout()).thenReturn(of(SCENARIO_TIMEOUT));
        when(options.getTimeout()).thenReturn(null);
        when(action.getTimeout()).thenReturn(actionTimeoutValue);
        when(actionTimeoutValue.toLong()).thenReturn(ACTION_TIMEOUT);
        
        Long actualTimeout = executor.getActionTimeout(action, context);
        
        assertThat(actualTimeout, is(ACTION_TIMEOUT));
    }
    
    @Test
    public void scenarioTimeoutChoosenIfActionTimeoutIsNotSet(){
        when(scenario.getTimeout()).thenReturn(of(SCENARIO_TIMEOUT));
        when(options.getTimeout()).thenReturn(null);
        
        Long actualTimeout = executor.getActionTimeout(action, context);
        
        assertThat(actualTimeout, is(of(SCENARIO_TIMEOUT).toLong()));
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
                RuntimeException exception) {
            super(listener);
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

        public TestBaseActionExecutor(ExecutionEventListeners listener) {
            super(listener);
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
