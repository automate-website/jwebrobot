package website.automate.jwebrobot.mapper.action;

import org.springframework.stereotype.Service;
import website.automate.waml.io.model.main.action.SelectAction;

@Service
public class SelectActionMapper extends FilterActionMapper<SelectAction> {

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
