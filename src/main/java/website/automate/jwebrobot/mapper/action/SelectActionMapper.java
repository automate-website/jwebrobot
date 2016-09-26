package website.automate.jwebrobot.mapper.action;

import website.automate.waml.io.model.action.SelectAction;

public class SelectActionMapper extends ElementStoreActionMapper<SelectAction> {

    @Override
    public SelectAction map(SelectAction source) {
        SelectAction target = new SelectAction();
        map(source, target);
        return target;
    }

    @Override
    public void map(SelectAction source, SelectAction target) {
        super.map(source, target);
    }

    @Override
    public Class<SelectAction> getSupportedType() {
        return SelectAction.class;
    }

}
