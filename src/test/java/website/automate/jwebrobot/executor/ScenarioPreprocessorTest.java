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
import website.automate.jwebrobot.model.Action;
import website.automate.jwebrobot.model.Scenario;

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
    @Mock private Map<String, Object> memory;
    @Mock private ScenarioExecutionContext context;
    @Mock private List<Action> actions;
    
    private ScenarioPreprocessor scenarioPreprocessor;
    
    @Before
    public void init(){
        scenarioPreprocessor = new ScenarioPreprocessor(expressionEvaluator);
        
        when(context.getMemory()).thenReturn(memory);
        when(scenario.getSteps()).thenReturn(actions);
    }
    
    @Test
    public void timeoutIsProcessed(){
        when(scenario.getTimeout()).thenReturn(TIMEOUT);
        when(expressionEvaluator.evaluate(TIMEOUT, memory)).thenReturn(PROCESSED_TIMEOUT);
        
        Scenario preprocessedScenario = scenarioPreprocessor.preprocess(scenario, context);
        
        assertThat(preprocessedScenario.getTimeout(), is(PROCESSED_TIMEOUT));
    }
    
    @Test
    public void ifIsProcessed(){
        when(scenario.getIf()).thenReturn(IF);
        when(expressionEvaluator.evaluate(IF, memory)).thenReturn(PROCESSED_IF);
        
        Scenario preprocessedScenario = scenarioPreprocessor.preprocess(scenario, context);
        
        assertThat(preprocessedScenario.getIf(), is(PROCESSED_IF));
    }
    
    @Test
    public void unlessIsProcessed(){
        when(scenario.getUnless()).thenReturn(UNLESS);
        when(expressionEvaluator.evaluate(UNLESS, memory)).thenReturn(PROCESSED_UNLESS);
        
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
        when(scenario.isFragment()).thenReturn(FRAGMENT);
        
        Scenario preprocessedScenario = scenarioPreprocessor.preprocess(scenario, context);
        
        assertThat(preprocessedScenario.isFragment(), is(FRAGMENT));
    }
    
    @Test
    public void descriptionIsNotProcessed(){
        when(scenario.getDescription()).thenReturn(DESCRIPTION);
        
        Scenario preprocessedScenario = scenarioPreprocessor.preprocess(scenario, context);
        
        assertThat(preprocessedScenario.getDescription(), is(DESCRIPTION));
    }
    
    @Test
    public void actionsAreNotProcessed(){
        when(scenario.getSteps()).thenReturn(actions);
        
        Scenario preprocessedScenario = scenarioPreprocessor.preprocess(scenario, context);
        
        assertThat(preprocessedScenario.getSteps(), is(actions));
    }
}
