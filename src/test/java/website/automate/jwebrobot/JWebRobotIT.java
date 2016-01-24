package website.automate.jwebrobot;

import org.junit.Test;

public class JWebRobotIT {

    private static final String ROOT_PACKAGE_DIRECTORY_PATH =  "./src/test/resources/website/automate/jwebrobot/";
    private static final String SCENARIO_PATH_PARAM_NAME = "scenarioPath";
    
    @Test
    public void simpleScenarioIsExecuted() {
        JWebRobot.main(new String [] {"--" + SCENARIO_PATH_PARAM_NAME + "=" + ROOT_PACKAGE_DIRECTORY_PATH + "wikipedia-test.yaml" });
    }
    
    @Test
    public void threeLevelInclusionScenarioIsExecuted(){
        JWebRobot.main(new String [] {"--" + SCENARIO_PATH_PARAM_NAME + "=" + ROOT_PACKAGE_DIRECTORY_PATH + "inclusion" });
    }
}
