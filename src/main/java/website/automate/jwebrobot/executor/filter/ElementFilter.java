package website.automate.jwebrobot.executor.filter;

import java.util.List;

import org.openqa.selenium.WebElement;

import website.automate.waml.io.model.CriterionType;

public interface ElementFilter {

    CriterionType getSupportedType();

    List<WebElement> filter(String value, List<WebElement> webElements);
    
    List<WebElement> filter(String value, WebElement webElement);
}
