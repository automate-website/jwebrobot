package website.automate.jwebrobot.mapper.action;

import org.springframework.stereotype.Service;
import website.automate.waml.io.model.main.action.ClickAction;

@Service
public class ClickActionMapper extends FilterActionMapper<ClickAction> {

    @Override
    public ClickAction map(ClickAction source) {
        ClickAction target = new ClickAction();
        map(source, target);
        return target;
    }

    @Override
    public void map(ClickAction source, ClickAction target) {
        super.map(source, target);
    }

    @Override
    public Class<ClickAction> getSupportedType() {
        return ClickAction.class;
    }

}
