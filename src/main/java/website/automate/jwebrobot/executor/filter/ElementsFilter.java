package website.automate.jwebrobot.executor.filter;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.waml.io.model.main.action.FilterAction;
import website.automate.waml.io.model.main.criteria.FilterCriteria;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.singletonList;

@Service
public class ElementsFilter {

    private SelectorElementFilter selectorElementFilter;

    private ValueElementFilter valueElementFilter;

    private TextElementFilter textElementFilter;

    @Autowired
    public ElementsFilter(SelectorElementFilter selectorElementFilter,
                          ValueElementFilter valueElementFilter,
                          TextElementFilter textElementFilter){
        this.selectorElementFilter = selectorElementFilter;
        this.valueElementFilter = valueElementFilter;
        this.textElementFilter = textElementFilter;
    }

    public <T extends FilterAction> WebElement filter(ScenarioExecutionContext context, T action){
        FilterCriteria filterCriteria = action.getFilter();

        List<WebElement> elements = getRoots(filterCriteria, context);

        String selector = filterCriteria.getSelector();
        if(selector != null){
            elements = getDisplayed(context, selectorElementFilter.filter(selector, elements));
        }

        String text = filterCriteria.getText();
        if(text != null){
            elements = getDisplayed(context, textElementFilter.filter(text, elements));
        }

        String value = filterCriteria.getValue();
        if(value != null){
            elements = getDisplayed(context, valueElementFilter.filter(value, elements));
        }

        return getFirstOrNull(elements);
    }

    private List<WebElement> getRoots(FilterCriteria filterCriteria, ScenarioExecutionContext context){
        String parent = filterCriteria.getParent();
        String element = filterCriteria.getElement();

        List<WebElement> elements;
        if(element != null){
            elements = singletonList(WebElement.class.cast(context.getTotalMemory().get(element)));
        }
        else if(parent != null){
            elements = singletonList(WebElement.class.cast(context.getTotalMemory().get(parent)));
        } else {
            elements = singletonList(getDefaultFrameElement(context.getDriver()));
        }
        return getDisplayed(context, elements);
    }

    private WebElement getFirstOrNull(List<WebElement> webElements){
        if(webElements.isEmpty()){
            return null;
        }
        return webElements.get(0);
    }

    protected List<WebElement> getDisplayed(ScenarioExecutionContext context, List<WebElement> webElements) {
        List<WebElement> displayedElements = new ArrayList<>();
        for (WebElement webElement : webElements) {
            if (webElement.isDisplayed()) {
                if(isIframe(webElement)){
                    return singletonList(switchToFrame(context, webElement));
                }
                displayedElements.add(webElement);
            }
        }
        return displayedElements;
    }

    private boolean isIframe(WebElement webElement){
        return webElement.getTagName().equalsIgnoreCase("iframe");
    }

    private WebElement switchToFrame(ScenarioExecutionContext context, WebElement webElement){
        return getDefaultFrameElement(context.getDriver().switchTo().frame(webElement));
    }

    private WebElement getDefaultFrameElement(WebDriver driver){
        return driver.findElement(By.tagName("html"));
    }
}
