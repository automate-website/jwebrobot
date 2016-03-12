package website.automate.jwebrobot.mapper.action;

import website.automate.waml.io.model.action.EnterAction;

public class EnterActionMapper extends FilterActionMapper<EnterAction> {

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
