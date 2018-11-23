package website.automate.jwebrobot.mapper.action;

import org.springframework.stereotype.Service;
import website.automate.waml.io.model.main.action.IncludeAction;
import website.automate.waml.io.model.main.criteria.IncludeCriteria;

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

        IncludeCriteria sourceInclude = new IncludeCriteria();
        IncludeCriteria targetInclude = new IncludeCriteria();
        targetInclude.setScenario(sourceInclude.getScenario());
        target.setInclude(targetInclude);
    }

    @Override
    public Class<IncludeAction> getSupportedType() {
        return IncludeAction.class;
    }

}
