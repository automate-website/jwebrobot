package website.automate.jwebrobot.mapper.action;

import org.springframework.stereotype.Service;
import website.automate.waml.io.model.main.action.EnterAction;
import website.automate.waml.io.model.main.criteria.EnterCriteria;

@Service
public class EnterActionMapper extends FilterActionMapper<EnterAction> {

    @Override
    public EnterAction map(EnterAction source) {
        EnterAction target = new EnterAction();
        map(source, target);
        return target;
    }

    @Override
    public void map(EnterAction source, EnterAction target) {
        super.map(source, target);

        EnterCriteria sourceEnter = new EnterCriteria();
        EnterCriteria targetEnter = new EnterCriteria();
        targetEnter.setInput(sourceEnter.getInput());
        target.setEnter(targetEnter);
    }

    @Override
    public Class<EnterAction> getSupportedType() {
        return EnterAction.class;
    }

}
