package website.automate.jwebrobot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import website.automate.jwebrobot.context.GlobalExecutionContext;
import website.automate.jwebrobot.executor.ScenarioExecutor;
import website.automate.jwebrobot.executor.WebDriverProvider;
import website.automate.jwebrobot.loader.ScenarioLoader;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class JWebRobotTest {

  @InjectMocks
  private JWebRobot jWebRobot;

  @Mock
  private ScenarioLoader scenarioLoader;

  @Mock
  private ScenarioExecutor scenarioExecutor;

  @Captor
  private ArgumentCaptor<GlobalExecutionContext> globalExecutionContextArgumentCaptor;

  @Test
  public void browserShouldBeSelectable() throws Exception {
    jWebRobot.run(new String[] {"-" + JWebRobotIT.BROWSER_PARAM_NAME, "chrome",
        "-" + JWebRobotIT.SCENARIO_PATH_PARAM_NAME, JWebRobotIT.ROOT_PACKAGE_DIRECTORY_PATH});

    verify(scenarioExecutor).execute(globalExecutionContextArgumentCaptor.capture());
    assertThat(globalExecutionContextArgumentCaptor.getValue().getOptions().getWebDriverType(),
        is(WebDriverProvider.Type.CHROME));
  }
}
