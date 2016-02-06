package website.automate.jwebrobot.executor.filter;

import static java.util.Arrays.asList;

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
        Map<CriteriaType, CriteriaValue> criteriaValueMap = action.getFilterCriteria();
        
        if(criteriaValueMap.isEmpty()){
            return Collections.emptyList();
        }
        
        WebElement html = context.getDriver().findElement(By.tagName("html"));
        
        List<WebElement> filteredWebElements = asList(html);
        for(Entry<CriteriaType, CriteriaValue> criteriaValueEntry : criteriaValueMap.entrySet()){
            ElementFilter elementFilter = elementFilterProvider.getInstance(criteriaValueEntry.getKey());
            filteredWebElements = elementFilter.filter(criteriaValueEntry.getValue(), filteredWebElements);
        }
        return filteredWebElements;
    }
}
