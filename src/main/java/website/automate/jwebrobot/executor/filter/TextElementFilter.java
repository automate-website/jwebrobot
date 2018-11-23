package website.automate.jwebrobot.executor.filter;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Service;

import static website.automate.waml.io.model.main.criteria.FilterCriteria.*;

@Service
public class TextElementFilter extends BaseElementFilter {

    @Override
    public FilterType getSupportedType() {
        return FilterType.TEXT;
    }

    @Override
    public List<WebElement> filter(String value, List<WebElement> webElements) {
        List<WebElement> foundWebElements = new ArrayList<>();

        for (WebElement webElement : webElements) {
            foundWebElements.addAll(webElement.findElements(By
                    .xpath("descendant-or-self::*[contains(.,'"
                            + value.toString() + "')]")));
        }
        return foundWebElements;
    }
}
