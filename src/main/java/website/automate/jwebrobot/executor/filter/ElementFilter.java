package website.automate.jwebrobot.executor.filter;

import java.util.List;

import org.openqa.selenium.WebElement;
import website.automate.waml.io.model.main.criteria.FilterCriteria;

public interface ElementFilter {

    FilterCriteria.FilterType getSupportedType();

    List<WebElement> filter(String value, List<WebElement> webElements);
    
    List<WebElement> filter(String value, WebElement webElement);
}
