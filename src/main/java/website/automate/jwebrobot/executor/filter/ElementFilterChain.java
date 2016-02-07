package website.automate.jwebrobot.executor.filter;

import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.google.inject.Inject;

import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.model.Action;
import website.automate.jwebrobot.model.CriteriaType;
import website.automate.jwebrobot.model.CriteriaValue;

public class ElementFilterChain {

    private ElementFilterProvider elementFilterProvider;
    
    @Inject
    public ElementFilterChain(ElementFilterProvider elementFilterProvider){
        this.elementFilterProvider = elementFilterProvider;
    }
    
    public List<WebElement> filter(ScenarioExecutionContext context, Action action){
        List<Map<CriteriaType, CriteriaValue>> filterCriteriaValueMaps = getParentCriteriaValueMapsInReverseOrder(action);
        
        if(filterCriteriaValueMaps.isEmpty()){
            return Collections.emptyList();
        }
        
        WebElement html = context.getDriver().findElement(By.tagName("html"));
        
        List<WebElement> filteredWebElements = asList(html);
        for(Map<CriteriaType, CriteriaValue> filterCriteriaValueMap : filterCriteriaValueMaps){
            for(Entry<CriteriaType, CriteriaValue> filterCriteriaValueEntry : filterCriteriaValueMap.entrySet()){
                ElementFilter elementFilter = elementFilterProvider.getInstance(filterCriteriaValueEntry.getKey());
                filteredWebElements = elementFilter.filter(filterCriteriaValueEntry.getValue(), filteredWebElements);
            }
        }
        return filteredWebElements;
    }
    
    private List<Map<CriteriaType, CriteriaValue>> getParentCriteriaValueMapsInReverseOrder(Action action){
        List<Map<String, CriteriaValue>> rawCriteriaValueMaps = new ArrayList<>();
        populateCriteriaValueMapsInReverseOrder(rawCriteriaValueMaps, action.getCriteriaValueMap());
        
        List<Map<CriteriaType, CriteriaValue>> criteriaValueMaps = new ArrayList<>();
        for(Map<String, CriteriaValue> rawCriteriaValueMap : rawCriteriaValueMaps){
            criteriaValueMaps.add(Action.getFilterCriteria(rawCriteriaValueMap));
        }
        return criteriaValueMaps;
    }
    
    private void populateCriteriaValueMapsInReverseOrder(List<Map<String, CriteriaValue>> criteriaValueMaps,
            Map<String, CriteriaValue> criteriaValueMap){
        CriteriaValue parentCriterion = criteriaValueMap.get(CriteriaType.PARENT.getName());
        if(parentCriterion != null){
            populateCriteriaValueMapsInReverseOrder(criteriaValueMaps, parentCriterion.asMap());
        }
        criteriaValueMaps.add(criteriaValueMap);
    }
}
