package website.automate.jwebrobot;

import com.google.inject.AbstractModule;
import com.google.inject.util.Modules;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import website.automate.jwebrobot.config.*;
import website.automate.jwebrobot.config.logger.LoggerModule;
import website.automate.jwebrobot.context.GlobalExecutionContext;
import website.automate.jwebrobot.executor.ScenarioExecutor;
import website.automate.jwebrobot.executor.WebDriverProvider;
import website.automate.jwebrobot.loader.ScenarioLoader;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class JWebRobotTest extends AbstractTest {


    @Captor
    private ArgumentCaptor<GlobalExecutionContext> globalExecutionContextArgumentCaptor;

    @Before
    public void setUp() {
        injector = GuiceInjector.recreateInstance(Modules.combine(
            new LoggerModule(),
            new ActionExecutorModule(),
            new ExpressionEvaluatorModule(),
            new ContextValidatorModule(),
            new ExecutionEventListenerModule(),
            new MockScenarioExecutorModule(),
            new MockScenarioLoaderModule()
        ));
    }

    @Test
    public void browserShouldBeSelectable(){
        ScenarioExecutor scenarioExecutor = injector.getInstance(ScenarioExecutor.class);

        JWebRobot.main(new String [] {"-" + JWebRobotIT.BROWSER_PARAM_NAME, "chrome", "-" + JWebRobotIT.SCENARIO_PATH_PARAM_NAME, JWebRobotIT.ROOT_PACKAGE_DIRECTORY_PATH});

        verify(scenarioExecutor).execute(globalExecutionContextArgumentCaptor.capture());
        assertThat(globalExecutionContextArgumentCaptor.getValue().getOptions().getWebDriverType(), is(WebDriverProvider.Type.CHROME));
    }

    private class MockScenarioExecutorModule extends AbstractModule {
        @Override
        protected void configure() {
            bind(ScenarioExecutor.class).toInstance(Mockito.mock(ScenarioExecutor.class));
        }
    }

    private class MockScenarioLoaderModule extends AbstractModule {
        @Override
        protected void configure() {
            bind(ScenarioLoader.class).toInstance(Mockito.mock(ScenarioLoader.class));
        }
    }
}
