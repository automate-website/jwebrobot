package website.automate.jwebrobot.mapper.action;

import org.springframework.stereotype.Service;
import website.automate.waml.io.model.action.AlertAction;

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
        target.setConfirm(source.getConfirm());
        target.setText(source.getText());
        target.setInput(source.getInput());
    }

    @Override
    public Class<AlertAction> getSupportedType() {
        return AlertAction.class;
    }

}
