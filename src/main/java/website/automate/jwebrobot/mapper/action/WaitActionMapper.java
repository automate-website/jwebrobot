package website.automate.jwebrobot.mapper.action;

import website.automate.waml.io.model.action.WaitAction;

public class WaitActionMapper extends ConditionalActionMapper<WaitAction> {

    @Override
    public WaitAction map(WaitAction source) {
        WaitAction target = new WaitAction();
        map(source, target);
        return target;
    }

    @Override
    public void map(WaitAction source, WaitAction target) {
        super.map(source, target);
        target.setTime(source.getTime());
    }

    @Override
    public Class<WaitAction> getSupportedType() {
        return WaitAction.class;
    }

}
