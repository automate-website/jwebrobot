package website.automate.jwebrobot.mapper.action;

import org.springframework.stereotype.Service;
import website.automate.waml.io.model.main.action.DefineAction;
import website.automate.waml.io.model.main.criteria.DefineCriteria;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

@Service
public class StoreActionMapper extends ConditionalActionMapper<DefineAction> {

    @Override
    public DefineAction map(DefineAction source) {
        DefineAction target = new DefineAction();
        map(source, target);
        return target;
    }

    @Override
    public void map(DefineAction source, DefineAction target) {
        super.map(source, target);

        DefineCriteria sourceDefine = source.getDefine();
        DefineCriteria targetDefine = new DefineCriteria();

        Map<String, Object> sourceValue = sourceDefine.getFacts();
        Map<String, Object> targetValue = new HashMap<>();
        for(Entry<String, Object> sourceValueEntry : sourceValue.entrySet()){
            targetValue.put(sourceValueEntry.getKey(), sourceValueEntry.getValue());
        }

        targetDefine.setFacts(targetValue);
        target.setDefine(targetDefine);
    }

    @Override
    public Class<DefineAction> getSupportedType() {
        return DefineAction.class;
    }

}
