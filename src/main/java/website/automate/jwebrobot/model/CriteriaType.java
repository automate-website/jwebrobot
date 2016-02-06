package website.automate.jwebrobot.model;

import static java.util.Arrays.asList;

import java.util.List;

public enum CriteriaType {
    CLEAR("clear"),
    IF("if"),
    UNLESS("unless"),
    SELECTOR("selector"),
    SCENARIO("scenario"),
    TEXT("text"),
    TIME("time"),
    TIMEOUT("timeout"),
    URL("url"),
    VALUE("value"),
    INPUT("input"),
    PARENT("parent");

    public static final List<CriteriaType> FILTER_CRITERIA_TYPES = asList(SELECTOR, TEXT, VALUE);
    
    private final String name;
    
    private CriteriaType(String name){
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
    public static CriteriaType findByName(String name){
        for(CriteriaType criteriaType : values()){
            if(criteriaType.getName().equals(name)){
                return criteriaType;
            }
        }
        return null;
    }
}
