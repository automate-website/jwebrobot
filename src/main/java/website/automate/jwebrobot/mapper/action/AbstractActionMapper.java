package website.automate.jwebrobot.mapper.action;


import website.automate.jwebrobot.utils.Mapper;
import website.automate.waml.io.model.action.Action;

import com.google.inject.Inject;

public class AbstractActionMapper {

    private ActionMapperProvider actionMapperProvider;
    
    @Inject
    public AbstractActionMapper(ActionMapperProvider actionMapperProvider){
        this.actionMapperProvider = actionMapperProvider;
    }
    
    public Action map(Action action){
        Mapper<Action, Action> actionMapperImpl = actionMapperProvider.getInstance(action.getClass());
        return actionMapperImpl.map(action);
    }
}
