package website.automate.jwebrobot.mapper.action;

import org.springframework.stereotype.Service;
import website.automate.waml.io.model.action.IncludeAction;

@Service
public class IncludeActionMapper extends ConditionalActionMapper<IncludeAction> {

    @Override
    public IncludeAction map(IncludeAction source) {
        IncludeAction target = new IncludeAction();
        map(source, target);
        return target;
    }

    @Override
    public void map(IncludeAction source, IncludeAction target) {
        super.map(source, target);
        target.setScenario(source.getScenario());
    }

    @Override
    public Class<IncludeAction> getSupportedType() {
        return IncludeAction.class;
    }

}
