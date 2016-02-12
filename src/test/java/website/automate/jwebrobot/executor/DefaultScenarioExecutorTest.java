package website.automate.jwebrobot.executor;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.executor.DefaultScenarioExecutor;
import website.automate.jwebrobot.executor.WebDriverProvider;
import website.automate.jwebrobot.executor.action.ActionExecutorFactory;
import website.automate.jwebrobot.expression.ConditionalExpressionEvaluator;
import website.automate.jwebrobot.listener.ExecutionEventListeners;
import website.automate.jwebrobot.model.Scenario;
import website.automate.jwebrobot.validator.ContextValidators;

@RunWith(MockitoJUnitRunner.class)
public class DefaultScenarioExecutorTest {

    @Mock private WebDriverProvider webDriverProvider;
    @Mock private ActionExecutorFactory actionExecutorFactory;
    @Mock private ExecutionEventListeners listener;
    @Mock private ContextValidators validator;
    @Mock private ConditionalExpressionEvaluator conditionalExpressionEvaluator;
    @Mock private ScenarioPreprocessor scenarioPreprocessor;
    
    @Mock private Scenario scenario;
    @Mock private Scenario preprocessedScenario;
    @Mock private ScenarioExecutionContext context;
    
    private DefaultScenarioExecutor scenarioExecutor;
    
    @Before
    public void init(){
        scenarioExecutor = new DefaultScenarioExecutor(webDriverProvider, actionExecutorFactory, listener, validator, conditionalExpressionEvaluator,
                scenarioPreprocessor);
        
        when(scenarioPreprocessor.preprocess(scenario, context)).thenReturn(preprocessedScenario);
    }
    
    @Test
    public void listenerNotInvokedForScenarioIfNotExecutable(){
        when(conditionalExpressionEvaluator.isExecutable(scenario, context)).thenReturn(false);
        
        scenarioExecutor.runScenario(scenario, context);
        
        verify(listener, never()).beforeScenario(context);
        verify(listener, never()).afterScenario(context);
    }
    
    @Test
    public void listenerIsInvokedBeforeScenarioIfExecutable(){
        when(conditionalExpressionEvaluator.isExecutable(scenario, context)).thenReturn(true);
        
        scenarioExecutor.runScenario(scenario, context);
        
        verify(listener).beforeScenario(context);
    }
    
    @Test
    public void listenerIsInvokedAfterScenarioIfExecutable(){
        when(conditionalExpressionEvaluator.isExecutable(scenario, context)).thenReturn(true);
        
        scenarioExecutor.runScenario(scenario, context);
        
        verify(listener).afterScenario(context);
    }
}
