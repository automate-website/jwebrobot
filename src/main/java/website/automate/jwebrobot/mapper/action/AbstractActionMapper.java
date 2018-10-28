package website.automate.jwebrobot.mapper.action;


import website.automate.jwebrobot.utils.Mapper;
import website.automate.waml.io.model.action.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AbstractActionMapper {

    private ActionMapperProvider actionMapperProvider;
    
    @Autowired
    public AbstractActionMapper(ActionMapperProvider actionMapperProvider){
        this.actionMapperProvider = actionMapperProvider;
    }
    
    @SuppressWarnings("unchecked")
    public <T extends Action> T map(T action){
        Mapper<T,T> actionMapperImpl = (Mapper<T, T>) actionMapperProvider.getInstance(action.getClass());
        return actionMapperImpl.map(action);
    }
}
