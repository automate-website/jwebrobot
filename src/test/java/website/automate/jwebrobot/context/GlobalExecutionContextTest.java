package website.automate.jwebrobot.context;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import website.automate.jwebrobot.executor.ExecutorOptions;
import website.automate.jwebrobot.loader.ScenarioFile;
import website.automate.waml.io.model.main.Scenario;

import java.io.File;
import java.util.Map;

import static java.util.Arrays.asList;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GlobalExecutionContextTest {

    private static final String
        SCENARIO_PATH = "../foo/bar.yaml";
    
    @Mock private Scenario scenario;
    @Mock private File file;
    @Mock private ScenarioFile scenarioFile;
    @Mock private ExecutorOptions options;
    @Mock private Map<String, Object> memory;
    
    @Before
    public void init(){
        when(scenario.getPath()).thenReturn(SCENARIO_PATH);
        when(scenarioFile.getFile()).thenReturn(file);
        when(scenarioFile.getScenario()).thenReturn(scenario);
    }
    
    @Test
    public void optionsAreSet(){
        GlobalExecutionContext context = new GlobalExecutionContext(asList(scenarioFile), options, memory);
        
        assertThat(context.getOptions(), is(options));
    }
    
    @Test
    public void scenariosFoundByName(){
        GlobalExecutionContext context = new GlobalExecutionContext(asList(scenarioFile), options, memory);
        
        assertThat(context.getScenarioByPath(SCENARIO_PATH), is(scenario));
    }
    
    @Test
    public void scenariosReferenceFile(){
        GlobalExecutionContext context = new GlobalExecutionContext(asList(scenarioFile), options, memory);
        
        assertThat(context.getFile(scenario), is(file));
    }
    
    @Test
    public void memoryIsSet(){
        GlobalExecutionContext context = new GlobalExecutionContext(asList(scenarioFile), options, memory);
        
        assertThat(context.getMemory(), is(memory));
    }
}
