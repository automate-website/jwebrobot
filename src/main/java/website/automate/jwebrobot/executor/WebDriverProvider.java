package website.automate.jwebrobot.executor;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.opera.OperaDriver;

public class WebDriverProvider {

    public WebDriver createInstance(Type type) {
        switch (type) {
            case FIREFOX:
                return new FirefoxDriver();
            case CHROME:
                return new ChromeDriver();
            case OPERA:
                return new OperaDriver();
            default:
                throw new RuntimeException("Unsupported web driver type.");
        }
    }

    public enum Type {
        FIREFOX,
        CHROME,
        OPERA;

        public static Type fromString(String typeString) {
            if (typeString != null) {
                for (Type type : Type.values()) {
                    if (typeString.equalsIgnoreCase(type.name())) {
                        return type;
                    }
                }
            }

            return null;
        }
    }


}
