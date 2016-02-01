package website.automate.jwebrobot;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(separators = "=")
public class ConfigurationProperties {
    @Parameter(names = "--scenarioPath", description = "Path to a single WAML scenario or WAML project root directory.", required = true)
    private String scenarioPath;

    @Parameter(names = "--browser", description = "Browser to use for execution (e.g firefox or chrome).", required = false)
    private String browser = "firefox";

    public String getScenarioPath() {
        return scenarioPath;
    }

    public void setScenarioPath(String scenarioPath) {
        this.scenarioPath = scenarioPath;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }
}
