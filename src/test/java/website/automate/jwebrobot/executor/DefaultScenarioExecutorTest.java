package website.automate.jwebrobot.executor;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import org.mockito.junit.MockitoJUnitRunner;
import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.executor.action.ActionExecutorFactory;
import website.automate.jwebrobot.executor.action.ActionPreprocessor;
import website.automate.jwebrobot.expression.ConditionalExpressionEvaluator;
import website.automate.jwebrobot.listener.ExecutionEventListeners;
import website.automate.jwebrobot.mapper.action.AbstractActionMapper;
import website.automate.jwebrobot.mapper.action.ActionMapperProvider;
import website.automate.jwebrobot.validator.ContextValidators;
import website.automate.waml.io.model.Scenario;

@RunWith(MockitoJUnitRunner.class)
public class DefaultScenarioExecutorTest {

    @Mock private WebDriverProvider webDriverProvider;
    @Mock private ActionExecutorFactory actionExecutorFactory;
    @Mock private ExecutionEventListeners listener;
    @Mock private ContextValidators validator;
    @Mock private ConditionalExpressionEvaluator conditionalExpressionEvaluator;
    @Mock private ScenarioPreprocessor scenarioPreprocessor;
    @Mock private ActionPreprocessor actionPreprocessor;
    @Mock private ActionMapperProvider actionMapperProvider;
    
    @Mock private Scenario scenario;
    @Mock private Scenario preprocessedScenario;
    @Mock private ScenarioExecutionContext context;
    @Mock private AbstractActionMapper abstractActionMapper;
    @Mock private ScenarioPatternFilter scenarioPatternFilter;
    @Mock private ActionExecutorUtils actionExecutorUtils;
    
    private DefaultScenarioExecutor scenarioExecutor;
    
    @Before
    public void init(){
        scenarioExecutor = new DefaultScenarioExecutor(webDriverProvider, actionExecutorFactory, listener,
                validator, conditionalExpressionEvaluator,
                scenarioPreprocessor, actionPreprocessor, abstractActionMapper,
                scenarioPatternFilter, actionExecutorUtils);
        
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
