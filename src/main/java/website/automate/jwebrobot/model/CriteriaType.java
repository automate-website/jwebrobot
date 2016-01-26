package website.automate.jwebrobot.model;

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
    DEFAULT("default");

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
