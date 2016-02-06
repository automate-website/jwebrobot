package website.automate.jwebrobot.executor.filter;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.google.inject.Inject;

import website.automate.jwebrobot.model.CriteriaType;

public class ElementFilterProvider {

    private Map<CriteriaType, ElementFilter> criteriaTypeElementFilterMap = new HashMap<>();
    
    @Inject
    public ElementFilterProvider(Set<ElementFilter> elementFilters){
        for(ElementFilter elementFilter : elementFilters){
            criteriaTypeElementFilterMap.put(elementFilter.getSupportedType(), elementFilter);
        }
    }
    
    public ElementFilter getInstance(CriteriaType type){
        return criteriaTypeElementFilterMap.get(type);
    }
}
