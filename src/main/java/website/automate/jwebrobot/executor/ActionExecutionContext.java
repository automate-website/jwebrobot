package website.automate.jwebrobot.executor;

import java.util.Map;

import org.openqa.selenium.WebDriver;

public class ActionExecutionContext {

    private WebDriver driver;
    
    private Map<String, String> memory;

    public ActionExecutionContext(WebDriver driver, Map<String, String> memory) {
        this.driver = driver;
        this.memory = memory;
    }
    
    public WebDriver getDriver() {
        return driver;
    }

    public Map<String, String> getMemory() {
        return memory;
    }
}
