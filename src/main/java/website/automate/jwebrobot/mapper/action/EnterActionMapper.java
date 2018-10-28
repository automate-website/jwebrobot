package website.automate.jwebrobot.mapper.action;

import org.springframework.stereotype.Service;
import website.automate.waml.io.model.action.EnterAction;

@Service
public class EnterActionMapper extends ElementStoreActionMapper<EnterAction> {

    @Override
    public EnterAction map(EnterAction source) {
        EnterAction target = new EnterAction();
        map(source, target);
        return target;
    }

    @Override
    public void map(EnterAction source, EnterAction target) {
        super.map(source, target);
        target.setInput(source.getInput());
    }

    @Override
    public Class<EnterAction> getSupportedType() {
        return EnterAction.class;
    }

}
