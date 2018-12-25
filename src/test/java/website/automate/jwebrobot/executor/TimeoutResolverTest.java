package website.automate.jwebrobot.executor;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import website.automate.jwebrobot.context.GlobalExecutionContext;
import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.waml.io.model.main.Scenario;
import website.automate.waml.io.model.main.action.TimeLimitedAction;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TimeoutResolverTest {

    private static final String SCENARIO_TIMEOUT = "3";

    private static final Long GLOBAL_TIMEOUT = 1L;

    private static final String ACTION_TIMEOUT = "2";

    @Mock
    private TimeLimitedAction action;

    @Mock
    private ScenarioExecutionContext context;

    @Mock
    private GlobalExecutionContext globalContext;

    @Mock
    private Scenario scenario;

    @Mock
    private ExecutorOptions options;

    private TimeoutResolver resolver = new TimeoutResolver();

    @Before
    public void init(){
        when(context.getScenario()).thenReturn(scenario);
        when(context.getGlobalContext()).thenReturn(globalContext);
        when(globalContext.getOptions()).thenReturn(options);
    }

    @Test
    public void defaultTimeoutChoosenIfNothingSet(){
        when(options.getTimeout()).thenReturn(null);

        Long actualTimeout = resolver.resolve(action, context);

        assertThat(actualTimeout, is(1L));
    }

    @Test
    public void actionTimeoutChoosenIfSet(){
        when(options.getTimeout()).thenReturn(null);
        when(action.getTimeout()).thenReturn(ACTION_TIMEOUT);

        Long actualTimeout = resolver.resolve(action, context);

        assertThat(actualTimeout, is(Long.parseLong(ACTION_TIMEOUT)));
    }

    @Test
    public void scenarioTimeoutChoosenIfActionTimeoutIsNotSet(){
        when(options.getTimeout()).thenReturn(null);

        Long actualTimeout = resolver.resolve(action, context);

        assertThat(actualTimeout, is(Long.parseLong(SCENARIO_TIMEOUT)));
    }

    @Test
    public void globalTimeoutChoosenIfSet(){
        when(options.getTimeout()).thenReturn(GLOBAL_TIMEOUT);

        Long actualTimeout = resolver.resolve(action, context);

        assertThat(actualTimeout, is(GLOBAL_TIMEOUT));
    }
}
