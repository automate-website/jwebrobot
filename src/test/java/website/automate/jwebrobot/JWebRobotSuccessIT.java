package website.automate.jwebrobot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import java.io.File;

import static website.automate.jwebrobot.TestUtils.getSamples;
import static website.automate.jwebrobot.TestUtils.getWebDriverUrl;

@RunWith(Parameterized.class)
public class JWebRobotSuccessIT {

    @Parameter
    public File scenarioFile;

    @Test
    public void scenarioIsExecutedSuccessful() {
        String scenarioPath = scenarioFile.getAbsolutePath();
        runScenario(scenarioPath);
    }

    private void runScenario(String scenarioPath){
        JWebRobot.main(new String [] {"-scenarioPath", scenarioPath, "-browserDriverUrl", getWebDriverUrl()});
    }

    @Parameters(name = "{index}: {0}")
    public static Iterable<? extends Object> data() {
        return getSamples("website/automate/jwebrobot/success");
    }
}
