package website.automate.jwebrobot.executor.filter;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Service;
import website.automate.waml.io.model.main.criteria.FilterCriteria.FilterType;

@Service
public class SelectorElementFilter extends BaseElementFilter {

    @Override
    public FilterType getSupportedType() {
        return FilterType.SELECTOR;
    }

    @Override
    public List<WebElement> filter(String value, List<WebElement> webElements) {
        String selector = value.toString();
        List<WebElement> foundWebElements = new ArrayList<>();
        for (WebElement webElement : webElements) {
            foundWebElements.addAll(webElement.findElements(By
                    .cssSelector(selector)));
        }
        return foundWebElements;
    }

}
