package website.automate.jwebrobot.executor.filter;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import website.automate.waml.io.model.CriterionType;
import website.automate.waml.io.model.CriterionValue;

public class SelectorElementFilter extends BaseElementFilter {

    @Override
    public CriterionType getSupportedType() {
        return CriterionType.SELECTOR;
    }

    @Override
    public List<WebElement> filter(CriterionValue value,
            List<WebElement> webElements) {
        String selector = value.toString();
        List<WebElement> foundWebElements = new ArrayList<>();
        for(WebElement webElement : webElements){
            foundWebElements.addAll(webElement.findElements(By.cssSelector(selector)));
        }
        return foundWebElements;
    }

}
