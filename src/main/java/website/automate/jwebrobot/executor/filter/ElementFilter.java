package website.automate.jwebrobot.executor.filter;

import java.util.List;

import org.openqa.selenium.WebElement;

import website.automate.waml.io.model.CriterionType;
import website.automate.waml.io.model.CriterionValue;

public interface ElementFilter {

    CriterionType getSupportedType();

    List<WebElement> filter(CriterionValue value, List<WebElement> webElements);
    
    List<WebElement> filter(CriterionValue value, WebElement webElement);
}
