package website.automate.jwebrobot;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import website.automate.jwebrobot.exceptions.DecimalNumberExpectedException;
import website.automate.jwebrobot.exceptions.WaitTimeTooBigException;

public class JWebRobotIT {

    public static final String ROOT_PACKAGE_DIRECTORY_PATH =  "./src/test/resources/website/automate/jwebrobot/";
    public static final String SCENARIO_PATH_PARAM_NAME = "scenarioPath";
    public static final String BROWSER_PARAM_NAME = "browser";

    @Test
    public void threeLevelInclusionScenarioIsExecuted(){
        JWebRobot.main(new String [] {"-" + SCENARIO_PATH_PARAM_NAME, ROOT_PACKAGE_DIRECTORY_PATH + "inclusion" });
    }

    @Test
    public void contextArgumentScenarioIsExecuted(){
        JWebRobot.main(new String [] {"-" + SCENARIO_PATH_PARAM_NAME,
                ROOT_PACKAGE_DIRECTORY_PATH + "context-argument-test.yaml",
                "-context",
                "baseUrl=https://en.wikipedia.org"});
    }

    @Test
    public void storeActionScenarioIsExecuted(){
        JWebRobot.main(new String [] {"-" + SCENARIO_PATH_PARAM_NAME,
                ROOT_PACKAGE_DIRECTORY_PATH + "store-action-test.yaml"});
    }

    @Test
    public void moveActionScenarioIsExecuted(){
        JWebRobot.main(new String [] {"-" + SCENARIO_PATH_PARAM_NAME,
                ROOT_PACKAGE_DIRECTORY_PATH + "move-action-test.yaml"});
    }

    @Test(expected = DecimalNumberExpectedException.class)
    public void shouldNotAcceptWaitTimeWithWrongFormat() {
        JWebRobot.main(new String [] {"-" + SCENARIO_PATH_PARAM_NAME,
            ROOT_PACKAGE_DIRECTORY_PATH + "failing/wait-time-wrong-format.yaml"});
    }


    @Test(expected = WaitTimeTooBigException.class)
    public void shouldNotAcceptTooBigWaitTimeFormat() {
        JWebRobot.main(new String [] {"-" + SCENARIO_PATH_PARAM_NAME,
            ROOT_PACKAGE_DIRECTORY_PATH + "failing/wait-time-too-big.yaml"});
    }

    @Ignore
    @Test
    @Category(ChromeTests.class)
    public void simpleScenarioShouldBeExecutedWithChrome() {
        JWebRobot.main(new String [] {
            "-" + SCENARIO_PATH_PARAM_NAME, ROOT_PACKAGE_DIRECTORY_PATH + "wikipedia-test.yaml",
            "-browser", "chrome"
        });
    }

    @Ignore
    @Test
    @Category(ChromeHeadlessTests.class)
    public void simpleScenarioShouldBeExecutedWithChromeHeadless() {
        JWebRobot.main(new String [] {
            "-" + SCENARIO_PATH_PARAM_NAME, ROOT_PACKAGE_DIRECTORY_PATH + "wikipedia-test.yaml",
            "-browser", "chrome-headless"
        });
    }
}
