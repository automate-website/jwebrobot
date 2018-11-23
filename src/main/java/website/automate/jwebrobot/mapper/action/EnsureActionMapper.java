package website.automate.jwebrobot.mapper.action;

import org.springframework.stereotype.Service;
import website.automate.waml.io.model.main.action.EnsureAction;

@Service
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
    }

    @Override
    public Class<EnsureAction> getSupportedType() {
        return EnsureAction.class;
    }

}
