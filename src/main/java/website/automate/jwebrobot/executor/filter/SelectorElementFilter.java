package website.automate.jwebrobot.executor.filter;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import website.automate.jwebrobot.model.CriteriaType;
import website.automate.jwebrobot.model.CriteriaValue;

public class SelectorElementFilter extends BaseElementFilter {

    @Override
    public CriteriaType getSupportedType() {
        return CriteriaType.SELECTOR;
    }

    @Override
    public List<WebElement> filter(CriteriaValue value,
            List<WebElement> webElements) {
        String selector = value.asString();
        List<WebElement> foundWebElements = new ArrayList<>();
        for(WebElement webElement : webElements){
            foundWebElements.addAll(webElement.findElements(By.cssSelector(selector)));
        }
        return foundWebElements;
    }

}
