package website.automate.jwebrobot.executor.filter;

import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.google.inject.Inject;

import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.waml.io.model.CriterionType;
import website.automate.waml.io.model.CriterionValue;
import website.automate.waml.io.model.action.FilterAction;
import website.automate.waml.io.model.action.ParentCriteria;

public class ElementFilterChain {

    private ElementFilterProvider elementFilterProvider;
    
    @Inject
    public ElementFilterChain(ElementFilterProvider elementFilterProvider){
        this.elementFilterProvider = elementFilterProvider;
    }
    
    public List<WebElement> filter(ScenarioExecutionContext context, FilterAction action){
        List<Map<CriterionType, CriterionValue>> filterCriteriaValueMaps = getParentCriteriaValueMapsInReverseOrder(action);
        
        if(filterCriteriaValueMaps.isEmpty()){
            return Collections.emptyList();
        }
        
        WebElement html = context.getDriver().findElement(By.tagName("html"));
        
        List<WebElement> filteredWebElements = asList(html);
        for(Map<CriterionType, CriterionValue> filterCriteriaValueMap : filterCriteriaValueMaps){
            for(Entry<CriterionType, CriterionValue> filterCriteriaValueEntry : filterCriteriaValueMap.entrySet()){
                ElementFilter elementFilter = elementFilterProvider.getInstance(filterCriteriaValueEntry.getKey());
                filteredWebElements = elementFilter.filter(filterCriteriaValueEntry.getValue(), filteredWebElements);
            }
        }
        return filteredWebElements;
    }
    
    private List<Map<CriterionType, CriterionValue>> getParentCriteriaValueMapsInReverseOrder(FilterAction action){
        List<Map<CriterionType, CriterionValue>> groupedFilterCriteria = new ArrayList<>();
        
        ParentCriteria parent = action.getParent();
        if(parent != null){
            groupedFilterCriteria.add(getParentFilterCriteria(parent));
        }
        groupedFilterCriteria.add(getActionFilterCriteria(action));
        
        return groupedFilterCriteria;
    }
    
    private Map<CriterionType, CriterionValue> getActionFilterCriteria(FilterAction action){
        Map<CriterionType, CriterionValue> filterCriteria = new HashMap<>();
        String selector = action.getSelector();
        String text = action.getText();
        String value = action.getValue();
        if(selector != null){
            filterCriteria.put(CriterionType.SELECTOR, CriterionValue.of(selector));
        }
        if(text != null){
            filterCriteria.put(CriterionType.TEXT, CriterionValue.of(text));
        }
        if(value != null){
            filterCriteria.put(CriterionType.VALUE, CriterionValue.of(value));
        }
        return filterCriteria;
    }
    
    private Map<CriterionType, CriterionValue> getParentFilterCriteria(ParentCriteria parent){
        Map<CriterionType, CriterionValue> filterCriteria = new HashMap<>();
        String selector = parent.getSelector();
        String text = parent.getText();
        String value = parent.getValue();
        if(selector != null){
            filterCriteria.put(CriterionType.SELECTOR, CriterionValue.of(selector));
        }
        if(text != null){
            filterCriteria.put(CriterionType.TEXT, CriterionValue.of(text));
        }
        if(value != null){
            filterCriteria.put(CriterionType.VALUE, CriterionValue.of(value));
        }
        return filterCriteria;
    }
}
