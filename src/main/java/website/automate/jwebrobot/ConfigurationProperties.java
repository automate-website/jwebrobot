package website.automate.jwebrobot;

import static java.text.MessageFormat.format;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import website.automate.jwebrobot.executor.ExecutorOptions.ScreenshotType;
import website.automate.jwebrobot.executor.ExecutorOptions.TakeScreenshots;

import com.beust.jcommander.Parameter;

public class ConfigurationProperties {

	@Parameter(names = "-scenarioPath", description = "Path to a single WAML scenario or WAML project root directory.", required = true)
    private String scenarioPath;

    @Parameter(names = "-browser", description = "Browser to use for execution (e.g firefox or chrome).", required = false)
    private String browser = "firefox";
    
    @Parameter(names = "-context", variableArity = true, required = false)
    private List<String> contextEntries = new ArrayList<>();
    
    @Parameter(names = "-takeScreenshots", description = "Determines if any and under which condition screenshots must be taken.", required = false)
    private String takeScreenshots = TakeScreenshots.ON_FAILURE.getName();

    @Parameter(names = "-screenshotPath", description = "Path to the directory where created screeshots must be saved.", required = false)
    private String screenshotPath = "./";
    
    @Parameter(names = "-screenshotType", description = "Defines the way screenshots must be taken: fullscreen vs. viewport")
    private String screenshotType = ScreenshotType.VIEW_PORT.getName();
    
    @Parameter(names = "-screenshotFormat", description = "Defines the screenshot format")
    private String screenshotFormat = "png";
    
    @Parameter(names = "-timeout", description = "Timeout waiting for conditions to be fulfilled in seconds.", required = false)
    private Long timeout;
    
    @Parameter(names = "-scenarioPattern", description = "Scenario name pattern. If set, only non fragment scenarios matching the pattern are executed.", required = false)
    private String scenarioPattern;
    
    @Parameter(names = "-reportPath", description = "Path to which the execution report is written to.", required = false)
    private String reportPath;
    
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

    public Long getTimeout() {
        return timeout;
    }

    public void setTimeout(Long timeout) {
        this.timeout = timeout;
    }
    
    public Map<String, Object> getContext(){
        Map<String, Object> contextMap = new HashMap<>();
        for(String contextEntry : contextEntries){
            String [] contextPair = contextEntry.split("=", 2);
            if(contextPair.length != 2){
                throw new IllegalArgumentException(format("Context entry {0} is incomplete.", contextEntry));
            }
            contextMap.put(contextPair[0], contextPair[1]);
        }
        return contextMap;
    }

    public void setContextEntries(List<String> contextEntries) {
        this.contextEntries = contextEntries;
    }

    public String getTakeScreenshots() {
        return takeScreenshots;
    }

    public void setTakeScreenshots(String takeScreenshots) {
        this.takeScreenshots = takeScreenshots;
    }

    public String getScreenshotPath() {
        return screenshotPath;
    }

    public void setScreenshotPath(String screenshotPath) {
        this.screenshotPath = screenshotPath;
    }

    public String getReportPath() {
        return reportPath;
    }

    public void setReportPath(String reportPath) {
        this.reportPath = reportPath;
    }

    public String getScenarioPattern() {
        return scenarioPattern;
    }

    public void setScenarioPattern(String scenarioPattern) {
        this.scenarioPattern = scenarioPattern;
    }

    public String getScreenshotType() {
		return screenshotType;
	}

	public void setScreenshotType(String screenshotType) {
		this.screenshotType = screenshotType;
	}

	public String getScreenshotFormat() {
		return screenshotFormat;
	}

	public void setScreenshotFormat(String screenshotFormat) {
		this.screenshotFormat = screenshotFormat;
	}
}
