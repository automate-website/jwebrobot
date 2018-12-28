package website.automate.jwebrobot.expression.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.expression.ExpressionEvaluator;
import website.automate.waml.io.model.main.action.OpenAction;
import website.automate.waml.io.model.main.criteria.OpenCriteria;

@Service
public class OpenActionExpressionEvaluator extends ConditionalActionExpressionEvaluator<OpenAction> {

    @Autowired
    public OpenActionExpressionEvaluator(
            ExpressionEvaluator expressionEvaluator) {
        super(expressionEvaluator);
    }

    @Override
    public void evaluateTemplateAsString(OpenAction action, ScenarioExecutionContext context) {
        super.evaluateTemplateAsString(action, context);
        OpenCriteria openCriteria = action.getOpen();
        openCriteria.setUrl(evaluateTemplateAsString(openCriteria.getUrl(), context));
    }

    @Override
    public Class<OpenAction> getSupportedType() {
        return OpenAction.class;
    }
}
