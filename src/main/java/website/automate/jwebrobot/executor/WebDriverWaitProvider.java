package website.automate.jwebrobot.executor;

import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;

import static java.util.Arrays.asList;

@Service
public class WebDriverWaitProvider {

    public WebDriverWait getInstance(WebDriver webDriver, long timeout){
        WebDriverWait webDriverWait = new WebDriverWait(webDriver, timeout);
        webDriverWait.ignoreAll(asList(StaleElementReferenceException.class));
        return webDriverWait;
    }
}
