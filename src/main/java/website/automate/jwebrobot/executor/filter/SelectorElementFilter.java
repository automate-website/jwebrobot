package website.automate.jwebrobot.executor.filter;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SelectorElementFilter extends BaseElementFilter {

    @Override
    public List<WebElement> filter(String value, List<WebElement> webElements) {
        List<WebElement> foundWebElements = new ArrayList<>();
        for (WebElement webElement : webElements) {
            foundWebElements.addAll(webElement.findElements(By
                    .cssSelector(value)));
        }
        return foundWebElements;
    }

}
