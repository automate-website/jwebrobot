package website.automate.jwebrobot.executor.filter;

import java.util.List;
import org.openqa.selenium.WebElement;

public interface ElementFilter {

    List<WebElement> filter(String value, List<WebElement> webElements);

    List<WebElement> filter(String value, WebElement webElement);
}
