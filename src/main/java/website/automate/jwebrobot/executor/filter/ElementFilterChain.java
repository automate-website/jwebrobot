package website.automate.jwebrobot.executor.filter;

import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.openqa.selenium.By;
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

        WebElement html = context.getDriver().findElement(By.tagName("html"));

        List<WebElement> filteredWebElements = asList(html);

        for (Map<CriterionType, String> filterCriteriaValueMap : filterCriteriaValueMaps) {
            for (Entry<CriterionType, String> filterCriteriaValueEntry : filterCriteriaValueMap
                    .entrySet()) {
                ElementFilter elementFilter = elementFilterProvider
                        .getInstance(filterCriteriaValueEntry.getKey());
                filteredWebElements = getDisplayed(elementFilter
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

    private List<WebElement> getDisplayed(List<WebElement> webElements) {
        List<WebElement> displayedElements = new ArrayList<>();
        for (WebElement webElement : webElements) {
            if (webElement.isDisplayed()) {
                displayedElements.add(webElement);
            }
        }
        return displayedElements;
    }
}
