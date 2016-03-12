package website.automate.jwebrobot.mapper.action;

import website.automate.waml.io.model.action.OpenAction;

public class OpenActionMapper extends ConditionalActionMapper<OpenAction> {

    @Override
    public OpenAction map(OpenAction source) {
        OpenAction target = new OpenAction();
        map(source, target);
        return target;
    }

    @Override
    public void map(OpenAction source, OpenAction target) {
        super.map(source, target);
        target.setUrl(source.getUrl());
    }

    @Override
    public Class<OpenAction> getSupportedType() {
        return OpenAction.class;
    }

}
