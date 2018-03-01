package website.automate.jwebrobot.executor;

import static java.lang.Boolean.parseBoolean;

import website.automate.jwebrobot.ConfigurationProperties;
import website.automate.waml.io.model.report.LogEntry.LogLevel;

public class ExecutorOptions {

    public enum TakeScreenshots {
        NEVER("NEVER"), ON_FAILURE("ON_FAILURE"), ON_EVERY_STEP("ON_EVERY_STEP");

        private String name;

        private TakeScreenshots(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public static TakeScreenshots findByName(String name) {
            for (TakeScreenshots takeScreenshots : values()) {
                if (takeScreenshots.getName().equalsIgnoreCase(name)) {
                    return takeScreenshots;
                }
            }
            return null;
        }
    }
    
    public enum ScreenshotType {
    	VIEW_PORT("VIEW_PORT"),
    	FULLSCREEN("FULLSCREEN");
    	
    	private String name;

        private ScreenshotType(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public static ScreenshotType findByName(String name) {
            for (ScreenshotType screenshotType : values()) {
                if (screenshotType.getName().equalsIgnoreCase(name)) {
                    return screenshotType;
                }
            }
            return null;
        }
    }
    
    public enum Mode {
        
        NON_INTERACTIVE("NON_INTERACTIVE"),
        INTERACTIVE("INTERACTIVE");
        
        private Mode(String name){
            this.name = name;
        }
        
        private String name;
        
        public String getName(){
            return name;
        }
        
        public static Mode findByName(String name) {
            for (Mode mode : values()) {
                if (mode.getName().equalsIgnoreCase(name)) {
                    return mode;
                }
            }
            return null;
        }
    }

    private WebDriverProvider.Type webDriverType = WebDriverProvider.Type.FIREFOX;

    public String screenshotPath;

    private TakeScreenshots takeScreenshots;

    private ScreenshotType screenshotType;
    
    private Long timeout;

    private String scenarioPattern;
    
    private String reportPath;

    private String screenshotFormat;
    
    private LogLevel browserLogLevel;
    
    private boolean maximizeWindow;
    
    private Mode mode;
    
    public static ExecutorOptions of(
            ConfigurationProperties configurationProperties) {
        ExecutorOptions executorOptions = new ExecutorOptions();

        executorOptions.setWebDriverType(WebDriverProvider.Type
                .fromString(configurationProperties.getBrowser()));
        executorOptions.setScreenshotPath(configurationProperties
                .getScreenshotPath());
        executorOptions.setTakeScreenshots(TakeScreenshots
                .findByName(configurationProperties.getTakeScreenshots()));
        executorOptions.setTimeout(configurationProperties.getTimeout());
        executorOptions.setScenarioPattern(configurationProperties.getScenarioPattern());
        executorOptions.setReportPath(configurationProperties.getReportPath());
        executorOptions.setScreenshotType(ScreenshotType.findByName(configurationProperties.getScreenshotType()));
        executorOptions.setScreenshotFormat(configurationProperties.getScreenshotFormat());
        executorOptions.setBrowserLogLevel(LogLevel.valueOf(configurationProperties.getBrowserLogLevel().toUpperCase()));
        executorOptions.setMaximizeWindow(parseBoolean(configurationProperties.getMaximizeWindow()));
        executorOptions.setMode(Mode.findByName(configurationProperties.getMode()));
        
        return executorOptions;
    }

    public WebDriverProvider.Type getWebDriverType() {
        return webDriverType;
    }

    public void setWebDriverType(WebDriverProvider.Type webDriverType) {
        this.webDriverType = webDriverType;
    }

    public String getScreenshotPath() {
        return screenshotPath;
    }

    public void setScreenshotPath(String screenshotPath) {
        this.screenshotPath = screenshotPath;
    }

    public TakeScreenshots getTakeScreenshots() {
        return takeScreenshots;
    }

    public void setTakeScreenshots(TakeScreenshots takeScreenshots) {
        this.takeScreenshots = takeScreenshots;
    }

    public Long getTimeout() {
        return timeout;
    }

    public void setTimeout(Long timeout) {
        this.timeout = timeout;
    }

    public String getScenarioPattern() {
        return scenarioPattern;
    }

    public void setScenarioPattern(String scenarioPattern) {
        this.scenarioPattern = scenarioPattern;
    }

    public String getReportPath() {
        return reportPath;
    }

    public void setReportPath(String reportPath) {
        this.reportPath = reportPath;
    }

    public ScreenshotType getScreenshotType() {
		return screenshotType;
	}

	public void setScreenshotType(ScreenshotType screenshotType) {
		this.screenshotType = screenshotType;
	}

	public String getScreenshotFormat() {
		return screenshotFormat;
	}

	public void setScreenshotFormat(String screenshotFormat) {
		this.screenshotFormat = screenshotFormat;
	}

    public LogLevel getBrowserLogLevel() {
        return browserLogLevel;
    }

    public void setBrowserLogLevel(LogLevel browserLogLevel) {
        this.browserLogLevel = browserLogLevel;
    }

    public boolean isMaximizeWindow() {
        return maximizeWindow;
    }

    public void setMaximizeWindow(boolean maximizeWindow) {
        this.maximizeWindow = maximizeWindow;
    }

    public Mode getMode() {
        return mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }
}
