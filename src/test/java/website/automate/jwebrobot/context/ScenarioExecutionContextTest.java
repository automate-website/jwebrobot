package website.automate.jwebrobot.context;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openqa.selenium.WebDriver;

import website.automate.jwebrobot.model.Scenario;

@RunWith(MockitoJUnitRunner.class)
public class ScenarioExecutionContextTest {

    @Mock private GlobalExecutionContext globalContext;
    @Mock private Scenario scenario;
    @Mock private WebDriver driver;
    @Mock private Map<String, Object> memory;
    @Mock private Scenario childScenario;
    
    @Test
    public void childContextShouldBeCreatedWithProperReferences(){
        ScenarioExecutionContext context = new ScenarioExecutionContext(globalContext, scenario, driver, memory);
        
        ScenarioExecutionContext childContext = context.createChildContext(childScenario);
        
        assertThat(childContext.getDriver(), is(context.getDriver()));
        assertThat(childContext.getMemory(), is(context.getMemory()));
        assertThat(childContext.getGlobalContext(), is(context.getGlobalContext()));
        assertThat(childContext.getScenario(), is(childScenario));
        assertThat(childContext.getParent(), is(context));
    }
}
