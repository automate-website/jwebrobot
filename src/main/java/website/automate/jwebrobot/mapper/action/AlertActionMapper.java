package website.automate.jwebrobot.mapper.action;

import org.springframework.stereotype.Service;
import website.automate.waml.io.model.main.action.AlertAction;
import website.automate.waml.io.model.main.criteria.AlertCriteria;

@Service
public class AlertActionMapper extends ConditionalActionMapper<AlertAction> {

    @Override
    public AlertAction map(AlertAction source) {
        AlertAction target = new AlertAction();
        map(source, target);
        return target;
    }

    @Override
    public void map(AlertAction source, AlertAction target) {
        super.map(source, target);

        AlertCriteria sourceAlert = new AlertCriteria();
        AlertCriteria targetAlert = new AlertCriteria();
        targetAlert.setConfirm(sourceAlert.getConfirm());
        targetAlert.setInput(sourceAlert.getInput());
        targetAlert.setText(sourceAlert.getText());
    }

    @Override
    public Class<AlertAction> getSupportedType() {
        return AlertAction.class;
    }

}
