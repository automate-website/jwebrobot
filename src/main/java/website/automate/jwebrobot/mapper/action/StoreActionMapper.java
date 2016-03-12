package website.automate.jwebrobot.mapper.action;

import static website.automate.waml.io.model.CriterionValue.of;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import website.automate.waml.io.model.CriterionValue;
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
        
        Map<String, CriterionValue> sourceValue = source.getValue();
        Map<String, CriterionValue> targetValue = new HashMap<>();
        for(Entry<String, CriterionValue> sourceValueEntry : sourceValue.entrySet()){
            targetValue.put(sourceValueEntry.getKey(), of(sourceValueEntry.getValue()));
        }
        
        target.setValue(targetValue);
    }

    @Override
    public Class<StoreAction> getSupportedType() {
        return StoreAction.class;
    }

}
