package website.automate.jwebrobot.model.mapper;

import website.automate.jwebrobot.exceptions.TooManyActionsException;
import website.automate.jwebrobot.model.Action;
import website.automate.jwebrobot.utils.CollectionMapper;

import java.util.Map;

public class StepsMapper extends CollectionMapper<Object, Action> {

    @SuppressWarnings("unchecked")
    @Override
    public Action map(Object source) {
        Action target = new Action();

        Map<String, Object> actionMap = (Map<String, Object>) source;

        if (actionMap.keySet().size() != 1) {
            throw new TooManyActionsException(actionMap.keySet().size());
        }

        target.init(actionMap);

        return target;
    }


    @Override
    public void map(Object source, Action target) {
        throw new UnsupportedOperationException();
    }

}
