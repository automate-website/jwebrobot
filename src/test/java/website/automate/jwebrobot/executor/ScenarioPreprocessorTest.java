package website.automate.jwebrobot.executor;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.expression.ExpressionEvaluator;
import website.automate.waml.io.model.Scenario;
import website.automate.waml.io.model.action.Action;

@RunWith(MockitoJUnitRunner.class)
public class ScenarioPreprocessorTest {

    private static final String
        TIMEOUT = "timout",
        IF = "if",
        UNLESS = "unless",
        DESCRIPTION = "desc",
        NAME = "name",
        PROCESSED_TIMEOUT = "processed timeout",
        PROCESSED_IF = "processed if",
        PROCESSED_UNLESS = "processed unless";
    
    private static final Integer PRECEDENCE = 1;
    
    private static final boolean FRAGMENT = true;
        
    
    @Mock private Scenario scenario;
    @Mock private ExpressionEvaluator expressionEvaluator;
    @Mock private Map<String, Object> totalMemory;
    @Mock private ScenarioExecutionContext context;
    @Mock private List<Action> actions;
    
    private ScenarioPreprocessor scenarioPreprocessor;
    
    @Before
    public void init(){
        scenarioPreprocessor = new ScenarioPreprocessor(expressionEvaluator);
        
        when(context.getTotalMemory()).thenReturn(totalMemory);
        when(scenario.getActions()).thenReturn(actions);
    }
    
    @Test
    public void timeoutIsProcessed(){
        when(scenario.getTimeout()).thenReturn(TIMEOUT);
        when(expressionEvaluator.evaluate(TIMEOUT, totalMemory)).thenReturn(PROCESSED_TIMEOUT);
        
        Scenario preprocessedScenario = scenarioPreprocessor.preprocess(scenario, context);
        
        assertThat(preprocessedScenario.getTimeout(), is(PROCESSED_TIMEOUT));
    }
    
    @Test
    public void ifIsProcessed(){
        when(scenario.getWhen()).thenReturn(IF);
        when(expressionEvaluator.evaluate(IF, totalMemory)).thenReturn(PROCESSED_IF);
        
        Scenario preprocessedScenario = scenarioPreprocessor.preprocess(scenario, context);
        
        assertThat(preprocessedScenario.getWhen(), is(PROCESSED_IF));
    }
    
    @Test
    public void unlessIsProcessed(){
        when(scenario.getUnless()).thenReturn(UNLESS);
        when(expressionEvaluator.evaluate(UNLESS, totalMemory)).thenReturn(PROCESSED_UNLESS);
        
        Scenario preprocessedScenario = scenarioPreprocessor.preprocess(scenario, context);
        
        assertThat(preprocessedScenario.getUnless(), is(PROCESSED_UNLESS));
    }
    
    @Test
    public void nameIsNotProcessed(){
        when(scenario.getName()).thenReturn(NAME);
        
        Scenario preprocessedScenario = scenarioPreprocessor.preprocess(scenario, context);
        
        assertThat(preprocessedScenario.getName(), is(NAME));
    }
    
    @Test
    public void precendenceIsNotProcessed(){
        when(scenario.getPrecedence()).thenReturn(PRECEDENCE);
        
        Scenario preprocessedScenario = scenarioPreprocessor.preprocess(scenario, context);
        
        assertThat(preprocessedScenario.getPrecedence(), is(PRECEDENCE));
    }
    
    @Test
    public void fragmentIsNotProcessed(){
        when(scenario.getFragment()).thenReturn(FRAGMENT);
        
        Scenario preprocessedScenario = scenarioPreprocessor.preprocess(scenario, context);
        
        assertThat(preprocessedScenario.getFragment(), is(FRAGMENT));
    }
    
    @Test
    public void descriptionIsNotProcessed(){
        when(scenario.getDescription()).thenReturn(DESCRIPTION);
        
        Scenario preprocessedScenario = scenarioPreprocessor.preprocess(scenario, context);
        
        assertThat(preprocessedScenario.getDescription(), is(DESCRIPTION));
    }
    
    @Test
    public void actionsAreNotProcessed(){
        when(scenario.getActions()).thenReturn(actions);
        
        Scenario preprocessedScenario = scenarioPreprocessor.preprocess(scenario, context);
        
        assertThat(preprocessedScenario.getActions(), is(actions));
    }
}
