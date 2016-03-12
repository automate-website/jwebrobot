package website.automate.jwebrobot.executor.filter;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import website.automate.waml.io.model.CriterionType;
import website.automate.waml.io.model.CriterionValue;

public class ValueElementFilter extends BaseElementFilter {

    @Override
    public CriterionType getSupportedType() {
        return CriterionType.VALUE;
    }

    @Override
    public List<WebElement> filter(CriterionValue value,
            List<WebElement> webElements) {
        List<WebElement> foundWebElements = new ArrayList<>();
        for(WebElement webElement : webElements){
            foundWebElements.addAll(webElement.findElements(By.cssSelector("*[value~='" + value.toString()  + "']")));
        }
        return foundWebElements;
    }

}
