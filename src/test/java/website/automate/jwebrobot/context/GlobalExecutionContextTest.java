package website.automate.jwebrobot.context;

import static java.util.Arrays.asList;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import website.automate.jwebrobot.models.scenario.Scenario;

@RunWith(MockitoJUnitRunner.class)
public class GlobalExecutionContextTest {

    private static final String SCENARIO_TITLE = "awesome scenario";
    
    @Mock private Scenario scenario;
    
    @Test
    public void scenarioShouldBeReturnedByName(){
        when(scenario.getName()).thenReturn(SCENARIO_TITLE);
        
        GlobalExecutionContext context = new GlobalExecutionContext(asList(scenario));
        
        assertThat(context.getScenario(SCENARIO_TITLE), is(scenario));
    }
}
