package website.automate.jwebrobot.executor;

import website.automate.jwebrobot.ConfigurationProperties;

public class ExecutorOptions {

    public enum TakeScreenshots {
        NEVER("never"),
        ON_FAILURE("on_failure"),
        ON_EVERY_STEP("on_every_step");
        
        private String name;
        
        private TakeScreenshots(String name){
            this.name = name;
        }
        
        public String getName(){
            return name;
        }
        
        public static TakeScreenshots findByName(String name){
            for(TakeScreenshots takeScreenshots : values()){
                if(takeScreenshots.getName().equals(name)){
                    return takeScreenshots;
                }
            }
            return null;
        }
    }
    
    private WebDriverProvider.Type webDriverType = WebDriverProvider.Type.FIREFOX;
    
    public String screenshotPath;
    
    private TakeScreenshots takeScreenshots;
    
    public static ExecutorOptions of(ConfigurationProperties configurationProperties){
        ExecutorOptions executorOptions = new ExecutorOptions();
        
        executorOptions.setWebDriverType(WebDriverProvider.Type.fromString(configurationProperties.getBrowser()));
        executorOptions.setScreenshotPath(configurationProperties.getScreenshotPath());
        executorOptions.setTakeScreenshots(TakeScreenshots.findByName(configurationProperties.getTakeScreenshots()));
        
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
}
