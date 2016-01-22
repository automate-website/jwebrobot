package website.automate.jwebrobot.executor;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class WebDriverProvider {

    public WebDriver createInstance(Type type) {
        switch (type) {
            case FIREFOX:
                return new FirefoxDriver();
            default:
                throw new RuntimeException("Unsupported web driver type.");
        }
    }

    public enum Type {
        FIREFOX
    }
}
