package website.automate.jwebrobot.mapper.action;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import website.automate.jwebrobot.utils.Mapper;
import website.automate.waml.io.model.action.Action;
import website.automate.waml.io.model.action.ConditionalAction;

@Service
public class ActionMapperProvider {

    private Map<Class<? extends Action>, ConditionalActionMapper<? extends ConditionalAction>> actionMapperMap = new HashMap<>();
    
    @Autowired
    public ActionMapperProvider(Set<ConditionalActionMapper<? extends ConditionalAction>> actionMappers){
        for(ConditionalActionMapper<? extends ConditionalAction> actionMapper: actionMappers){
            actionMapperMap.put(actionMapper.getSupportedType(), actionMapper);
        }
    }
    
    @SuppressWarnings("unchecked")
    public <T extends Action> Mapper<T, T> getInstance(Class<T> actionClazz){
        return (Mapper<T, T>) actionMapperMap.get(actionClazz);
    }
}
