package website.automate.jwebrobot.model;

public enum ActionType {
    
    CLICK("click", CriteriaType.SELECTOR),
    ENSURE("ensure", CriteriaType.SELECTOR),
    ENTER("enter", CriteriaType.VALUE),
    INCLUDE("include", CriteriaType.SCENARIO),
    MOVE("move", CriteriaType.SELECTOR),
    OPEN("open", CriteriaType.URL),
    SELECT("select", CriteriaType.SELECTOR),
    WAIT("wait", CriteriaType.TIME),
    STORE("store", null);
    
    private final String name;
    
    private CriteriaType defaultCriteriaType;
    
    private ActionType(String name, CriteriaType defaultCriteriaType){
        this.name = name;
        this.defaultCriteriaType = defaultCriteriaType;
    }
    
    public CriteriaType getDefaultCriteriaType() {
        return defaultCriteriaType;
    }
    
    public String getName() {
        return name;
    }
    
    public static ActionType findByName(String name){
        for(ActionType actionType : values()){
            if(actionType.getName().equals(name)){
                return actionType;
            }
        }
        return null;
    }
}
