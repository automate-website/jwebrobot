package website.automate.jwebrobot.expression.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.expression.ExpressionEvaluator;
import website.automate.waml.io.model.main.action.EnterAction;
import website.automate.waml.io.model.main.criteria.EnterCriteria;

@Service
public class EnterActionExpressionEvaluator extends FilterActionExpressionEvaluator<EnterAction> {

    @Autowired
    public EnterActionExpressionEvaluator(
            ExpressionEvaluator expressionEvaluator) {
        super(expressionEvaluator);
    }

    @Override
    public void evaluateTemplateAsString(EnterAction action, ScenarioExecutionContext context) {
        super.evaluateTemplateAsString(action, context);

        EnterCriteria enterCriteria = action.getFilter();
        enterCriteria.setInput(evaluateTemplateAsString(enterCriteria.getInput(), context));
    }

    @Override
    public Class<EnterAction> getSupportedType() {
        return EnterAction.class;
    }
}
