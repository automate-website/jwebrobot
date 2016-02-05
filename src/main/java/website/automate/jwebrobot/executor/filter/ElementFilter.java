package website.automate.jwebrobot.executor.filter;

import java.util.List;

import org.openqa.selenium.WebElement;

import website.automate.jwebrobot.model.CriteriaType;
import website.automate.jwebrobot.model.CriteriaValue;

public interface ElementFilter {

    CriteriaType getSupportedType();

    List<WebElement> filter(CriteriaValue value, List<WebElement> webElements);
    
    List<WebElement> filter(CriteriaValue value, WebElement webElement);
}
