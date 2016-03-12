package website.automate.jwebrobot.executor.filter;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.google.inject.Inject;

import website.automate.waml.io.model.CriterionType;

public class ElementFilterProvider {

    private Map<CriterionType, ElementFilter> criteriaTypeElementFilterMap = new HashMap<>();
    
    @Inject
    public ElementFilterProvider(Set<ElementFilter> elementFilters){
        for(ElementFilter elementFilter : elementFilters){
            criteriaTypeElementFilterMap.put(elementFilter.getSupportedType(), elementFilter);
        }
    }
    
    public ElementFilter getInstance(CriterionType type){
        return criteriaTypeElementFilterMap.get(type);
    }
}
