package website.automate.jwebrobot;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(separators = "=")
public class ConfigurationProperties {
    @Parameter(names = "--scenarioPath", description = "Path to a single waml scenario or waml project root directory.", required = true)
    private String scenarioPath;

    public String getScenarioPath() {
        return scenarioPath;
    }

    public void setScenarioPath(String scenarioPath) {
        this.scenarioPath = scenarioPath;
    }
}
