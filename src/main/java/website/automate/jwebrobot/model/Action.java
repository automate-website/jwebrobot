package website.automate.jwebrobot.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import website.automate.jwebrobot.exceptions.UnknownActionException;

public class Action {

    private ActionType type;
    
    private Map<String, CriteriaValue> criteriaValueMap = new HashMap<>();
    
    public CriteriaValue getCriteria(CriteriaType type){
        return getCriteria(type, criteriaValueMap);
    }
    
    private static CriteriaValue getCriteria(CriteriaType type, Map<String, CriteriaValue> criteriaValueMap){
        return criteriaValueMap.get(type.getName());
    }
    
    public ActionType getType() {
        return type;
    }
    
    public void init(Map<String, Object> actionWrapper){
        Iterator<Entry<String, Object>> actionWrapperIterator = actionWrapper.entrySet().iterator();
        Entry<String, Object> action = actionWrapperIterator.next();
        this.type = findTypeByName(action.getKey());
        initCriteriaValueMap(action.getValue());
    }
    
    private ActionType findTypeByName(String actionName){
        ActionType type = ActionType.findByName(actionName);
        if(type == null){
            throw new UnknownActionException(actionName);
        }
        return type;
    }
    
    @SuppressWarnings("unchecked")
    private void initCriteriaValueMap(Object value){
        if(value instanceof Map){
            addAll(criteriaValueMap, (Map<String, Object>)value);
        } else {
            addDefault(value);
        }
    }
    
    private void addDefault(Object value){
        criteriaValueMap.put(type.getDefaultCriteriaType().getName(), new CriteriaValue(value));
    }
    
    @SuppressWarnings("unchecked")
    private void addAll(Map<String, CriteriaValue> criteriaValueMap, Map<String, Object> values){
        for(Entry<String, Object> entry : values.entrySet()){
            Object value = entry.getValue();
            if(value instanceof Map){
                Map<String, CriteriaValue> nestedCriteriaValueMap = new HashMap<>();
                criteriaValueMap.put(entry.getKey(), new CriteriaValue(nestedCriteriaValueMap));
                addAll(nestedCriteriaValueMap, (Map<String, Object>)value);
            } else {
                criteriaValueMap.put(entry.getKey(), new CriteriaValue(entry.getValue()));
            }
        }
    }
    
    public String getUrl(){
        return getCriteriaAsString(CriteriaType.URL);
    }
    
    public String getIf(){
        return getCriteriaAsString(CriteriaType.IF);
    }
    
    public String getUnless(){
        return getCriteriaAsString(CriteriaType.UNLESS);
    }
    
    public String getSelector(){
        return getCriteriaAsString(CriteriaType.SELECTOR);
    }
    
    public String getText(){
        return getCriteriaAsString(CriteriaType.TEXT);
    }
    
    public String getTime(){
        return getCriteriaAsString(CriteriaType.TIME);
    }
    
    public String getTimeout(){
        return getCriteriaAsString(CriteriaType.TIMEOUT);
    }

    public String getValue(){
        return getCriteriaAsString(CriteriaType.VALUE);
    }
    
    public String getInput(){
        return getCriteriaAsString(CriteriaType.INPUT);
    }
    
    public Boolean getClear(){
        return getCriteriaAsBoolean(CriteriaType.CLEAR);
    }
    
    public String getScenario(){
        return getCriteriaAsString(CriteriaType.SCENARIO);
    }
    
    private String getCriteriaAsString(CriteriaType type){
        CriteriaValue value = getCriteria(type);
        if(value != null){
            return value.asString();
        }
        return null;
    }
    
    public void setUrl(String url){
        criteriaValueMap.put(CriteriaType.URL.getName(), new CriteriaValue(url));
    }
    
    public void setSelector(String selector){
        criteriaValueMap.put(CriteriaType.SELECTOR.getName(), new CriteriaValue(selector));
    }
    
    public void setType(ActionType type) {
        this.type = type;
    }

    public Map<String, CriteriaValue> getCriteriaValueMap() {
        return criteriaValueMap;
    }

    public void setCriteriaValueMap(Map<String, CriteriaValue> criteriaValueMap) {
        this.criteriaValueMap = criteriaValueMap;
    }
    
    public Map<CriteriaType, CriteriaValue> getCriteriaValueMap(List<CriteriaType> criteriaTypes){
        return getCriteriaValueMap(criteriaValueMap, criteriaTypes);
    }
    
    public static Map<CriteriaType, CriteriaValue> getCriteriaValueMap(Map<String, CriteriaValue> criteriaValueMap, List<CriteriaType> criteriaTypes){
        Map<CriteriaType, CriteriaValue> filteredCriteriaValueMap = new LinkedHashMap<>();
        for(CriteriaType criteriaType : criteriaTypes){
            CriteriaValue criteriaValue = getCriteria(criteriaType, criteriaValueMap);
            if(criteriaValue != null){
                filteredCriteriaValueMap.put(criteriaType, criteriaValue);
            }
        }
        return filteredCriteriaValueMap;
    }
    
    public Map<CriteriaType, CriteriaValue> getFilterCriteria(){
        return getCriteriaValueMap(CriteriaType.FILTER_CRITERIA_TYPES);
    }
    
    public static Map<CriteriaType, CriteriaValue> getFilterCriteria(Map<String, CriteriaValue> criteriaValueMap){
        return getCriteriaValueMap(criteriaValueMap, CriteriaType.FILTER_CRITERIA_TYPES);
    }
    
    public boolean hasFilterCriteria(){
        return !getFilterCriteria().isEmpty();
    }
    
    public Boolean getAbsent(){
        return getCriteriaAsBoolean(CriteriaType.ABSENT);
    }
    
    private Boolean getCriteriaAsBoolean(CriteriaType type){
        CriteriaValue criteriaValue = getCriteria(type);
        if(criteriaValue == null){
            return Boolean.FALSE;
        }
        return criteriaValue.asBoolean();
    }
}
