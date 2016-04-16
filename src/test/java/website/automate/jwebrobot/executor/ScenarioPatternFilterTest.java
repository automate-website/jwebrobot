package website.automate.jwebrobot.executor;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ScenarioPatternFilterTest {

    private static final String 
        SCENARIO_NAME = "first",
        ANOTHER_SCENARIO_NAME = "second";
    
    private ScenarioPatternFilter scenarioPatternFilter = new ScenarioPatternFilter();
    
    @Test
    public void nullScenarioPatternReturnsIsExecutable(){
        assertTrue(scenarioPatternFilter.isExecutable(null, SCENARIO_NAME));
    }
    
    @Test
    public void matchingScenarioPatternReturnsIsExecutable(){
        assertTrue(scenarioPatternFilter.isExecutable("^first$", SCENARIO_NAME));
    }
    
    @Test
    public void blankScenarioPatternReturnsIsExecutable(){
        assertTrue(scenarioPatternFilter.isExecutable("", SCENARIO_NAME));
    }
    
    @Test
    public void nonMatchingScenarioPatternReturnsIsNotExecutable(){
        assertFalse(scenarioPatternFilter.isExecutable("^first$", ANOTHER_SCENARIO_NAME));
    }
}
