package website.automate.jwebrobot.executor;

import website.automate.jwebrobot.ConfigurationProperties;

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

    private WebDriverProvider.Type webDriverType = WebDriverProvider.Type.FIREFOX;

    public String screenshotPath;

    private TakeScreenshots takeScreenshots;

    private Long timeout;

    private String scenarioPattern;
    
    private String reportPath;

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
}
