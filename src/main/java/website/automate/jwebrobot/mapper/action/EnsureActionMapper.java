package website.automate.jwebrobot.mapper.action;

import website.automate.waml.io.model.action.EnsureAction;

public class EnsureActionMapper extends FilterActionMapper<EnsureAction> {

    @Override
    public EnsureAction map(EnsureAction source) {
        EnsureAction target = new EnsureAction();
        map(source, target);
        return target;
    }

    @Override
    public void map(EnsureAction source, EnsureAction target) {
        super.map(source, target);
        target.setAbsent(source.getAbsent());
    }

    @Override
    public Class<EnsureAction> getSupportedType() {
        return EnsureAction.class;
    }

}
