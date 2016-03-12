package website.automate.jwebrobot.context;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openqa.selenium.WebDriver;

import website.automate.waml.io.model.Scenario;

@RunWith(MockitoJUnitRunner.class)
public class ScenarioExecutionContextTest {

	private static final String
		SCENARIO_NAME = "scenario",
		CHILD_SCENARIO_NAME = "child-scenario";
	
    @Mock private GlobalExecutionContext globalContext;
    @Mock private Scenario scenario;
    @Mock private WebDriver driver;
    @Mock private Map<String, Object> memory;
    @Mock private Scenario childScenario;
    @Mock private Scenario separateScenario;

    private ScenarioExecutionContext context;
    private ScenarioExecutionContext childContext;
    
    @Before
    public void init(){
        context = new ScenarioExecutionContext(globalContext, scenario, driver, memory);
        childContext = context.createChildContext(childScenario);
        when(scenario.getName()).thenReturn(SCENARIO_NAME);
        when(childScenario.getName()).thenReturn(CHILD_SCENARIO_NAME);
    }
    
    @Test
    public void childContextShouldBeCreatedWithProperReferences(){
        assertThat(childContext.getDriver(), is(context.getDriver()));
        assertThat(childContext.getMemory(), is(context.getMemory()));
        assertThat(childContext.getGlobalContext(), is(context.getGlobalContext()));
        assertThat(childContext.getScenario(), is(childScenario));
        assertThat(childContext.getParent(), is(context));
    }
    
    @Test
    public void contextContainsScenario(){
        assertThat(context.containsScenario(scenario), is(true));
    }
    
    @Test
    public void childContextContainsScenario(){
        assertThat(childContext.containsScenario(scenario), is(true));
    }
    
    @Test
    public void childContextContainsChildScenario(){
        assertThat(childContext.containsScenario(childScenario), is(true));
    }
    
    @Test
    public void contextDoesNotContainSeparateScenario(){
        assertThat(context.containsScenario(separateScenario), is(false));
    }
    
    @Test
    public void childContextDoesNotContainSeparateScenario(){
        assertThat(childContext.containsScenario(separateScenario), is(false));
    }
    
    @Test
    public void contextScenarioInclusionPathIsReturned(){
    	assertThat(context.getScenarioInclusionPath(), is(SCENARIO_NAME));
    }
    
    @Test
    public void childContextScenarioInclusionPathIsReturned(){
    	assertThat(childContext.getScenarioInclusionPath(), is(SCENARIO_NAME + "/" + CHILD_SCENARIO_NAME));
    }
    
    @Test
    public void rootScenarioIsReturnedByChildContext(){
    	assertThat(childContext.getRootScenario(), is(scenario));
    }
    
    @Test
    public void rootScenarioIsReturnedByContext(){
    	assertThat(context.getRootScenario(), is(scenario));
    }
    
    @Test
    public void memoryIsSet(){
        assertThat(context.getMemory(), is(memory));
    }
}
