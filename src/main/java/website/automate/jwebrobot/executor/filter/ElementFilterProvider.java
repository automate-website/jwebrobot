package website.automate.jwebrobot.executor.filter;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import website.automate.waml.io.model.CriterionType;

@Service
public class ElementFilterProvider {

    private Map<CriterionType, ElementFilter> criteriaTypeElementFilterMap = new HashMap<>();
    
    @Autowired
    public ElementFilterProvider(Set<ElementFilter> elementFilters){
        for(ElementFilter elementFilter : elementFilters){
            criteriaTypeElementFilterMap.put(elementFilter.getSupportedType(), elementFilter);
        }
    }
    
    public ElementFilter getInstance(CriterionType type){
        return criteriaTypeElementFilterMap.get(type);
    }
}
