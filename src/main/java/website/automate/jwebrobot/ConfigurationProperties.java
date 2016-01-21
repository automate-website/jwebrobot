package website.automate.jwebrobot;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(separators = "=")
public class ConfigurationProperties {
    @Parameter(names = "--scenario", description = "Scenario filename", required = true)
    private String scenarioFilename;

    public String getScenarioFilename() {
        return scenarioFilename;
    }

    public void setScenarioFilename(String scenarioFilename) {
        this.scenarioFilename = scenarioFilename;
    }
}
