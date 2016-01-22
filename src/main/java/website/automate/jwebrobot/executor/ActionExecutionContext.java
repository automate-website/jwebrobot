package website.automate.jwebrobot.executor;

import org.openqa.selenium.WebDriver;

import java.util.Map;

public class ActionExecutionContext {

    private WebDriver driver;

    private Map<String, String> memory;

    private long timeout = 1;

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

    public long getTimeout() {
        return timeout;
    }
}
