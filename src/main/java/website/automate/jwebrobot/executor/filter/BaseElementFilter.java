package website.automate.jwebrobot.executor.filter;

import static java.util.Arrays.asList;

import java.util.List;

import org.openqa.selenium.WebElement;

import website.automate.jwebrobot.model.CriteriaValue;

public abstract class BaseElementFilter implements ElementFilter {

    @Override
    public List<WebElement> filter(CriteriaValue value, WebElement webElement) {
        return filter(value, asList(webElement));
    }
}
