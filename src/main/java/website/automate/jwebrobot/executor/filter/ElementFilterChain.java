package website.automate.jwebrobot.executor.filter;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.google.inject.Inject;

import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.waml.io.model.CriterionType;
import website.automate.waml.io.model.action.FilterAction;
import website.automate.waml.io.model.action.ParentCriteria;

public class ElementFilterChain {

    private ElementFilterProvider elementFilterProvider;

    @Inject
    public ElementFilterChain(ElementFilterProvider elementFilterProvider) {
        this.elementFilterProvider = elementFilterProvider;
    }

    public List<WebElement> filter(ScenarioExecutionContext context,
            FilterAction action) {
        List<Map<CriterionType, String>> filterCriteriaValueMaps = getParentCriteriaValueMapsInReverseOrder(action);

        if (filterCriteriaValueMaps.isEmpty()) {
            return Collections.emptyList();
        }

        WebElement html = getDefaultFrameElement(context.getDriver().switchTo().defaultContent());

        List<WebElement> filteredWebElements = asList(html);

        for (Map<CriterionType, String> filterCriteriaValueMap : filterCriteriaValueMaps) {
            for (Entry<CriterionType, String> filterCriteriaValueEntry : filterCriteriaValueMap
                    .entrySet()) {
                ElementFilter elementFilter = elementFilterProvider
                        .getInstance(filterCriteriaValueEntry.getKey());
                filteredWebElements = getDisplayed(context, elementFilter
                        .filter(filterCriteriaValueEntry.getValue(),
                                filteredWebElements));
            }
        }

        return filteredWebElements;
    }
    
    private List<Map<CriterionType, String>> getParentCriteriaValueMapsInReverseOrder(
            FilterAction action) {
        List<Map<CriterionType, String>> groupedFilterCriteria = new ArrayList<>();

        ParentCriteria parent = action.getParent();
        if (parent != null) {
            groupedFilterCriteria.add(getParentFilterCriteria(parent));
        }
        groupedFilterCriteria.add(getActionFilterCriteria(action));

        return groupedFilterCriteria;
    }

    private Map<CriterionType, String> getActionFilterCriteria(
            FilterAction action) {
        Map<CriterionType, String> filterCriteria = new LinkedHashMap<>();
        String selector = action.getSelector();
        String text = action.getText();
        String value = action.getValue();
        if (selector != null) {
            filterCriteria.put(CriterionType.SELECTOR, selector);
        }
        if (text != null) {
            filterCriteria.put(CriterionType.TEXT, text);
        }
        if (value != null) {
            filterCriteria.put(CriterionType.VALUE, value);
        }
        return filterCriteria;
    }

    private Map<CriterionType, String> getParentFilterCriteria(
            ParentCriteria parent) {
        Map<CriterionType, String> filterCriteria = new LinkedHashMap<>();
        String selector = parent.getSelector();
        String text = parent.getText();
        String value = parent.getValue();
        if (selector != null) {
            filterCriteria.put(CriterionType.SELECTOR, selector);
        }
        if (text != null) {
            filterCriteria.put(CriterionType.TEXT, text);
        }
        if (value != null) {
            filterCriteria.put(CriterionType.VALUE, value);
        }
        return filterCriteria;
    }

    private List<WebElement> getDisplayed(ScenarioExecutionContext context, List<WebElement> webElements) {
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
