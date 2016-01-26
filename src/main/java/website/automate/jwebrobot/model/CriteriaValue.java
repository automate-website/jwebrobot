package website.automate.jwebrobot.model;

public class CriteriaValue {

    private Object value;
    
    public CriteriaValue(Object value){
        this.value = value;
    }
    
    public String asString(){
        return value.toString();
    }
    
    public Boolean asBoolean(){
        return Boolean.class.cast(value);
    }
    
    public Integer asInteger(){
        return Integer.class.cast(value);
    }
    
    public Double asDouble(){
        return Double.class.cast(value);
    }
    
    public Object asObject(){
        return value;
    }
}
