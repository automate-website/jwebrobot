package website.automate.jwebrobot.model;

import java.util.Map;

public class CriteriaValue {

    private Object value;
    
    public CriteriaValue(Object value){
        this.value = value;
    }
    
    public String asString(){
        return value.toString();
    }
    
    public Boolean asBoolean(){
        return Boolean.parseBoolean(asString());
    }
    
    public Long asLong(){
        return Long.parseLong(value.toString());
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
    
    @SuppressWarnings("unchecked")
    public Map<String, CriteriaValue> asMap(){
        return (Map<String, CriteriaValue>)value;
    }
}
