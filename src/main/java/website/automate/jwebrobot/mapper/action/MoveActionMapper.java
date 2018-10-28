package website.automate.jwebrobot.mapper.action;

import org.springframework.stereotype.Service;
import website.automate.waml.io.model.action.MoveAction;

@Service
public class MoveActionMapper extends ElementStoreActionMapper<MoveAction> {

    @Override
    public MoveAction map(MoveAction source) {
        MoveAction target = new MoveAction();
        map(source, target);
        return target;
    }

    @Override
    public void map(MoveAction source, MoveAction target) {
        super.map(source, target);
    }

    @Override
    public Class<MoveAction> getSupportedType() {
        return MoveAction.class;
    }

}
