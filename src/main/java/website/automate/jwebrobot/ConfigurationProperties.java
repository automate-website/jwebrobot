package website.automate.jwebrobot;

import com.beust.jcommander.Parameter;
import website.automate.jwebrobot.executor.ExecutorOptions.ScreenshotType;
import website.automate.jwebrobot.executor.ExecutorOptions.TakeScreenshots;

import java.util.*;

import static java.text.MessageFormat.format;

public class ConfigurationProperties {

    public static final String
        DEFAULT_REPORT_PATH = "./report.yaml",
        DEFAULT_SCENARIO_PATH = "./",
        DEFAULT_BROWSER_LOG_LEVEL = "error",
        DEFAULT_MODE = "non_interactive";
    public static final String FIREFOX = "firefox";

    @Parameter(names = "-scenarioPath", description = "Path to a single WAML scenario or WAML project root directory.", required = false)
    private String scenarioPath = DEFAULT_SCENARIO_PATH;

	@Parameter(names = "-scenarioPaths", variableArity = true, description = "Path to a single WAML scenario or WAML project root directory.", required = false)
    private List<String> scenarioPaths = new ArrayList<>();

    @Parameter(names = "-browser", description = "Browser to use for execution (e.g firefox or chrome).", required = false)
    private String browser = System.getProperty("jwebrobot.browser", FIREFOX);

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
    private String reportPath = DEFAULT_REPORT_PATH;

    @Parameter(names = "-browserLogLevel", description = "Log level at which browser logs are included into the reports.", required = false)
    private String browserLogLevel = DEFAULT_BROWSER_LOG_LEVEL;

    @Parameter(names = "-maximizeWindow", description = "Triggers window maximization.", required = false)
    private String maximizeWindow = Boolean.FALSE.toString();

    @Parameter(names = "-mode", description = "Defines the working mode of the jwebrobot.", required = false)
    private String mode = DEFAULT_MODE;

    public List<String> getScenarioPaths() {
        return scenarioPaths;
    }

    public void setScenarioPaths(List<String> scenarioPaths) {
        this.scenarioPaths = scenarioPaths;
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
            String contextKey = contextPair[0];
            String contextValue = contextPair[1];
            if(contextPair.length != 2 || contextKey.isEmpty() || contextValue.isEmpty()){
                throw new IllegalArgumentException(format("Context entry {0} is incomplete.", contextEntry));
            }
            contextMap.put(contextKey, contextValue);
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

    public String getBrowserLogLevel() {
        return browserLogLevel;
    }

    public void setBrowserLogLevel(String browserLogLevel) {
        this.browserLogLevel = browserLogLevel;
    }

    public String getScenarioPath() {
        return scenarioPath;
    }

    public Collection<String> getAllScenarioPaths(){
        Set<String> scenarioPaths = new HashSet<>();
        String scenarioPath = getScenarioPath();
        if(!ConfigurationProperties.DEFAULT_SCENARIO_PATH.equals(scenarioPath)){
            scenarioPaths.add(scenarioPath);
        }
        scenarioPaths.addAll(getScenarioPaths());
        return scenarioPaths;
    }

    public void setScenarioPath(String scenarioPath) {
        this.scenarioPath = scenarioPath;
    }

    public String getMaximizeWindow() {
        return maximizeWindow;
    }

    public void setMaximizeWindow(String maximizeWindow) {
        this.maximizeWindow = maximizeWindow;
    }

    public String getMode() {
        return mode;
    }

    public boolean isInteractive(){
        return !DEFAULT_MODE.equals(mode);
    }

    public void setMode(String mode) {
        this.mode = mode;
    }
}
