package website.automate.jwebrobot.context;

import static java.util.Arrays.asList;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.io.File;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import website.automate.jwebrobot.executor.ExecutorOptions;
import website.automate.jwebrobot.loader.ScenarioFile;
import website.automate.jwebrobot.models.scenario.Scenario;

@RunWith(MockitoJUnitRunner.class)
public class GlobalExecutionContextTest {

    private static final String
        SCENARIO_TITLE = "awesome scenario",
        ANOTHER_SCENARIO_TITLE = "another awesome scenario";
    
    @Mock private Scenario anotherScenario;
    @Mock private Scenario scenario;
    @Mock private File file;
    @Mock private ScenarioFile scenarioFile;
    @Mock private ExecutorOptions options;
    
    @Before
    public void init(){
        when(scenario.getPrecedence()).thenReturn(1);
        when(anotherScenario.getPrecedence()).thenReturn(2);
        when(scenario.getName()).thenReturn(SCENARIO_TITLE);
        when(anotherScenario.getName()).thenReturn(ANOTHER_SCENARIO_TITLE);
        when(scenarioFile.getFile()).thenReturn(file);
        when(scenarioFile.getScenarios()).thenReturn(asList(scenario, anotherScenario));
    }
    
    @Test
    public void optionsAreSet(){
        GlobalExecutionContext context = new GlobalExecutionContext(asList(scenarioFile), options);
        
        assertThat(context.getOptions(), is(options));
    }
    
    @Test
    public void scenariosFoundByName(){
        GlobalExecutionContext context = new GlobalExecutionContext(asList(scenarioFile), options);
        
        assertThat(context.getScenario(SCENARIO_TITLE), is(scenario));
        assertThat(context.getScenario(ANOTHER_SCENARIO_TITLE), is(anotherScenario));
    }
    
    @Test
    public void scenariosReferenceFile(){
        GlobalExecutionContext context = new GlobalExecutionContext(asList(scenarioFile), options);
        
        assertThat(context.getFile(scenario), is(file));
        assertThat(context.getFile(anotherScenario), is(file));
    }
    
    @Test
    public void scenariosAreSortedByPrecedence(){
        GlobalExecutionContext context = new GlobalExecutionContext(asList(scenarioFile), options);
        
        assertThat(context.getScenarios(), is(asList(anotherScenario, scenario)));
    }
}
