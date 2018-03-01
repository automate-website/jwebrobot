package website.automate.jwebrobot.executor.filter;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.apache.commons.lang3.StringUtils.isBlank;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.waml.io.model.action.FilterAction;
import website.automate.waml.io.model.criteria.FilterCriteria;

@Service
public class ElementFilterChain {

    private SelectorElementFilter selectorElementFilter;

    private TextElementFilter textElementFilter;

    private ValueElementFilter valueElementFilter;

    @Autowired
    public ElementFilterChain(SelectorElementFilter selectorElementFilter,
            TextElementFilter textElementFilter, ValueElementFilter valueElementFilter) {
        this.selectorElementFilter = selectorElementFilter;
        this.textElementFilter = textElementFilter;
        this.valueElementFilter = valueElementFilter;
    }

    public List<WebElement> filter(ScenarioExecutionContext context, FilterAction<?> action) {
        WebElement html = getDefaultFrameElement(context.getDriver().switchTo().defaultContent());
        FilterCriteria criteria = action.getFilter();

        List<WebElement> webElements = getParentIfSetOrHtmlRoot(html, criteria, context);

        webElements = filterBySelectorIfSet(criteria, context, webElements);

        webElements = filterByTextIfSet(criteria, context, webElements);

        webElements = filterByValueIfSet(criteria, context, webElements);

        return webElements;
    }

    private List<WebElement> filterByValueIfSet(FilterCriteria criteria,
            ScenarioExecutionContext context, List<WebElement> webElements) {
        if (!isBlank(criteria.getValue())) {
            return getDisplayed(context,
                    valueElementFilter.filter(criteria.getValue(), webElements));
        }
        return webElements;
    }

    private List<WebElement> filterByTextIfSet(FilterCriteria criteria,
            ScenarioExecutionContext context, List<WebElement> webElements) {
        if (!isBlank(criteria.getText())) {
            return getDisplayed(context, textElementFilter.filter(criteria.getText(), webElements));
        }
        return webElements;
    }

    private List<WebElement> filterBySelectorIfSet(FilterCriteria criteria,
            ScenarioExecutionContext context, List<WebElement> webElements) {
        if (!isBlank(criteria.getSelector())) {
            return getDisplayed(context,
                    selectorElementFilter.filter(criteria.getSelector(), webElements));
        }
        return webElements;
    }

    private List<WebElement> getParentIfSetOrHtmlRoot(WebElement html, FilterCriteria criteria,
            ScenarioExecutionContext context) {
        if (!isBlank(criteria.getParent())) {
            return asList(
                    WebElement.class.cast(context.getTotalMemory().get(criteria.getParent())));
        }
        return asList(html);
    }

    private List<WebElement> getDisplayed(ScenarioExecutionContext context,
            List<WebElement> webElements) {
        List<WebElement> displayedElements = new ArrayList<>();
        for (WebElement webElement : webElements) {
            if (webElement.isDisplayed()) {
                if (isIframe(webElement)) {
                    return singletonList(switchToFrame(context, webElement));
                }
                displayedElements.add(webElement);
            }
        }
        return displayedElements;
    }

    private boolean isIframe(WebElement webElement) {
        return webElement.getTagName().equalsIgnoreCase("iframe");
    }

    private WebElement switchToFrame(ScenarioExecutionContext context, WebElement webElement) {
        return getDefaultFrameElement(context.getDriver().switchTo().frame(webElement));
    }

    private WebElement getDefaultFrameElement(WebDriver driver) {
        return driver.findElement(By.tagName("html"));
    }
}
