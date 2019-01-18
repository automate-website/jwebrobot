package website.automate.jwebrobot.executor;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.remote.CommandExecutor;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.HttpCommandExecutor;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.stereotype.Service;

import java.net.URL;

@Service
public class WebDriverProvider {

    public WebDriver createInstance(Type type) {
        switch (type) {
            case CHROME:
                return new ChromeDriver(getChromeOptions());
            case CHROME_HEADLESS:
                ChromeOptions options = getChromeOptions();
                options.addArguments("--headless", "--disable-gpu");
                return new ChromeDriver(options);
            case OPERA:
                return new OperaDriver(getOperaCapabilities());
            case FIREFOX:
            default:
                return new FirefoxDriver();
        }
    }

    public WebDriver createInstance(Type type, URL webDriverUrl){
        if(webDriverUrl == null){
            return createInstance(type);
        }

        return createRemoteInstance(type, webDriverUrl);
    }

    private WebDriver createRemoteInstance(Type type, URL webDriverUrl) {
        CommandExecutor commandExecutor = new HttpCommandExecutor(webDriverUrl);
        switch (type) {
            case CHROME:
            case CHROME_HEADLESS:
                return new RemoteWebDriver(commandExecutor, getChromeCapabilities());
            case OPERA:
                return new RemoteWebDriver(commandExecutor, DesiredCapabilities.operaBlink());
            case FIREFOX:
            default:
                return new RemoteWebDriver(commandExecutor, DesiredCapabilities.firefox());
        }
    }

    private ChromeOptions getChromeOptions(){
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        return options;
    }

    private DesiredCapabilities getChromeCapabilities(){
        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        ChromeOptions options = getChromeOptions();
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);
        return capabilities;
    }

    private DesiredCapabilities getOperaCapabilities(){
        DesiredCapabilities capabilities = DesiredCapabilities.operaBlink();
        String operaBinaryPath = System.getProperty("webdriver.opera.binary");
        if(operaBinaryPath != null){
            ChromeOptions options = new ChromeOptions();
            options.setBinary(operaBinaryPath);
            capabilities.setCapability(ChromeOptions.CAPABILITY, options);
        }
        return capabilities;
    }

    public enum Type {
        FIREFOX("firefox"),
        CHROME("chrome"),
        CHROME_HEADLESS("chrome-headless"),
        OPERA("opera");

        private final String canonicalName;

        Type(String canonicalName) {
            this.canonicalName = canonicalName;
        }

        public static Type fromString(String typeString) {
            for (Type type : Type.values()) {
                if (type.canonicalName.equalsIgnoreCase(typeString)) {
                    return type;
                }
            }

            return null;
        }
    }


}
