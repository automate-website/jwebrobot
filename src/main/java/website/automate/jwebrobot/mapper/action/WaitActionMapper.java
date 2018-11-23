package website.automate.jwebrobot.mapper.action;

import org.springframework.stereotype.Service;
import website.automate.waml.io.model.main.action.WaitAction;
import website.automate.waml.io.model.main.criteria.WaitCriteria;

@Service
public class WaitActionMapper extends ConditionalActionMapper<WaitAction> {

    @Override
    public WaitAction map(WaitAction source) {
        WaitAction target = new WaitAction();
        map(source, target);
        return target;
    }

    @Override
    public void map(WaitAction source, WaitAction target) {
        super.map(source, target);

        WaitCriteria sourceWait = source.getWait();
        WaitCriteria targetWait = new WaitCriteria();
        targetWait.setTime(sourceWait.getTime());
        target.setWait(targetWait);
    }

    @Override
    public Class<WaitAction> getSupportedType() {
        return WaitAction.class;
    }

}
