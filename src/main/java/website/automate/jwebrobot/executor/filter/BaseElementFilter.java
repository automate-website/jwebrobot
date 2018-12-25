package website.automate.jwebrobot.executor.filter;

import org.openqa.selenium.WebElement;

import java.util.List;

import static java.util.Arrays.asList;

public abstract class BaseElementFilter implements ElementFilter {

    @Override
    public List<WebElement> filter(String value, WebElement webElement) {
        return filter(value, asList(webElement));
    }

}
