package website.automate.jwebrobot;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class JWebRobotIT {

    public static final String ROOT_PACKAGE_DIRECTORY_PATH =  "./src/test/resources/website/automate/jwebrobot/";
    public static final String SCENARIO_PATH_PARAM_NAME = "scenarioPath";
    public static final String BROWSER_PARAM_NAME = "browser";

    @Test
    public void simpleScenarioIsExecuted() {
        JWebRobot.main(new String [] {"-" + SCENARIO_PATH_PARAM_NAME, ROOT_PACKAGE_DIRECTORY_PATH + "wikipedia-test.waml"});
    }

    @Test
    public void threeLevelInclusionScenarioIsExecuted(){
        JWebRobot.main(new String [] {"-" + SCENARIO_PATH_PARAM_NAME, ROOT_PACKAGE_DIRECTORY_PATH + "inclusion" });
    }
    
    @Test
    public void contextArgumentScenarioIsExecuted(){
        JWebRobot.main(new String [] {"-" + SCENARIO_PATH_PARAM_NAME, 
                ROOT_PACKAGE_DIRECTORY_PATH + "context-argument-test.waml",
                "-context",
                "baseUrl=https://en.wikipedia.org"});
    }
    
    @Test
    public void storeActionScenarioIsExecuted(){
        JWebRobot.main(new String [] {"-" + SCENARIO_PATH_PARAM_NAME, 
                ROOT_PACKAGE_DIRECTORY_PATH + "store-action-test.waml"});
    }
    
    @Test
    public void moveActionScenarioIsExecuted(){
        JWebRobot.main(new String [] {"-" + SCENARIO_PATH_PARAM_NAME, 
                ROOT_PACKAGE_DIRECTORY_PATH + "move-action-test.waml"});
    }
    
    @Ignore
    @Test
    @Category(ChromeTests.class)
    public void simpleScenarioShouldBeExecutedWithChrome() {
        JWebRobot.main(new String [] {
            "-" + SCENARIO_PATH_PARAM_NAME, ROOT_PACKAGE_DIRECTORY_PATH + "wikipedia-test.waml",
            "-browser", "chrome"
        });
    }
}
