package website.automate.jwebrobot.executor.filter;

import org.openqa.selenium.WebElement;

import java.util.List;

public interface ElementFilter {

    List<WebElement> filter(String value, List<WebElement> webElements);
    
    List<WebElement> filter(String value, WebElement webElement);
}
