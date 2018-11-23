package website.automate.jwebrobot.mapper.action;

import org.springframework.stereotype.Service;
import website.automate.waml.io.model.main.action.OpenAction;
import website.automate.waml.io.model.main.criteria.OpenCriteria;

@Service
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

        OpenCriteria sourceOpen = source.getOpen();
        OpenCriteria targetOpen = new OpenCriteria();
        targetOpen.setUrl(sourceOpen.getUrl());
        target.setOpen(targetOpen);
    }

    @Override
    public Class<OpenAction> getSupportedType() {
        return OpenAction.class;
    }

}
