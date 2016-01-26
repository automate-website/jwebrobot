package website.automate.jwebrobot.model;

public enum ActionType {
    
    CLICK("click"),
    ENSURE("ensure"),
    ENTER("enter"),
    INCLUDE("include"),
    MOVE("move"),
    OPEN("open"),
    SELECT("select"),
    WAIT("wait");
    
    private final String name;
    
    private ActionType(String name){
        this.name = name;
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
