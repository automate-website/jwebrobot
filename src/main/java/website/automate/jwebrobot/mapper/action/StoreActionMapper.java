package website.automate.jwebrobot.mapper.action;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import website.automate.waml.io.model.action.StoreAction;

public class StoreActionMapper extends ConditionalActionMapper<StoreAction> {

    @Override
    public StoreAction map(StoreAction source) {
        StoreAction target = new StoreAction();
        map(source, target);
        return target;
    }

    @Override
    public void map(StoreAction source, StoreAction target) {
        super.map(source, target);
        
        Map<String, String> sourceValue = source.getValue();
        Map<String, String> targetValue = new HashMap<>();
        for(Entry<String, String> sourceValueEntry : sourceValue.entrySet()){
            targetValue.put(sourceValueEntry.getKey(), sourceValueEntry.getValue());
        }
        
        target.setValue(targetValue);
    }

    @Override
    public Class<StoreAction> getSupportedType() {
        return StoreAction.class;
    }

}
