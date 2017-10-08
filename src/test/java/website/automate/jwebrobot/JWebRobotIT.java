package website.automate.jwebrobot;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import website.automate.jwebrobot.exceptions.DecimalNumberExpectedException;
import website.automate.jwebrobot.exceptions.WaitTimeTooBigException;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class JWebRobotIT {

    @Autowired
    private JWebRobot jWebRobot;
  
    public static final String ROOT_PACKAGE_DIRECTORY_PATH =  "./src/test/resources/website/automate/jwebrobot/";
    public static final String SCENARIO_PATH_PARAM_NAME = "scenarioPath";
    public static final String BROWSER_PARAM_NAME = "browser";

    @Test
    public void simpleScenarioIsExecuted() throws Exception {
      jWebRobot.run(new String [] {"-" + SCENARIO_PATH_PARAM_NAME, ROOT_PACKAGE_DIRECTORY_PATH + "wikipedia-test.yaml"});
    }

    @Test
    public void threeLevelInclusionScenarioIsExecuted() throws Exception {
      jWebRobot.run(new String [] {"-" + SCENARIO_PATH_PARAM_NAME, ROOT_PACKAGE_DIRECTORY_PATH + "inclusion" });
    }

    @Test
    public void contextArgumentScenarioIsExecuted() throws Exception {
      System.setProperty("webdriver.chrome.driver","C:\\Program Files (x86)\\Google\\Chrome\\driver\\chromedriver.exe");
      jWebRobot.run(new String [] {"-" + SCENARIO_PATH_PARAM_NAME,
                ROOT_PACKAGE_DIRECTORY_PATH + "context-argument-test.yaml",
                "-context",
                "baseUrl=https://en.wikipedia.org"});
    }

    @Test
    public void storeActionScenarioIsExecuted() throws Exception {
      jWebRobot.run(new String [] {"-" + SCENARIO_PATH_PARAM_NAME,
                ROOT_PACKAGE_DIRECTORY_PATH + "store-action-test.yaml"});
    }

    @Test
    public void moveActionScenarioIsExecuted() throws Exception {
      jWebRobot.run(new String [] {"-" + SCENARIO_PATH_PARAM_NAME,
                ROOT_PACKAGE_DIRECTORY_PATH + "move-action-test.yaml"});
    }

    @Test(expected = DecimalNumberExpectedException.class)
    public void shouldNotAcceptWaitTimeWithWrongFormat() throws Exception {
      jWebRobot.run(new String [] {"-" + SCENARIO_PATH_PARAM_NAME,
            ROOT_PACKAGE_DIRECTORY_PATH + "failing/wait-time-wrong-format.yaml"});
    }


    @Test(expected = WaitTimeTooBigException.class)
    public void shouldNotAcceptTooBigWaitTimeFormat() throws Exception {
      jWebRobot.run(new String [] {"-" + SCENARIO_PATH_PARAM_NAME,
            ROOT_PACKAGE_DIRECTORY_PATH + "failing/wait-time-too-big.yaml"});
    }

    @Ignore
    @Test
    @Category(ChromeTests.class)
    public void simpleScenarioShouldBeExecutedWithChrome() throws Exception {
      jWebRobot.run(new String [] {
            "-" + SCENARIO_PATH_PARAM_NAME, ROOT_PACKAGE_DIRECTORY_PATH + "wikipedia-test.yaml",
            "-browser", "chrome"
        });
    }

    @Ignore
    @Test
    @Category(ChromeHeadlessTests.class)
    public void simpleScenarioShouldBeExecutedWithChromeHeadless() throws Exception {
      jWebRobot.run(new String [] {
            "-" + SCENARIO_PATH_PARAM_NAME, ROOT_PACKAGE_DIRECTORY_PATH + "wikipedia-test.yaml",
            "-browser", "chrome-headless"
        });
    }
}
