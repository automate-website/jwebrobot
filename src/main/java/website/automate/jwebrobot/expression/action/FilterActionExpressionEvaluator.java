package website.automate.jwebrobot.expression.action;

import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.expression.ExpressionEvaluator;
import website.automate.waml.io.model.main.action.FilterAction;

public abstract class FilterActionExpressionEvaluator<T extends FilterAction> extends TimeLimitedActionExpressionEvaluator<T> {

    public FilterActionExpressionEvaluator(
            ExpressionEvaluator expressionEvaluator) {
        super(expressionEvaluator);
    }

    @Override
    public void evaluate(T action, ScenarioExecutionContext context) {
        super.evaluate(action, context);
        action.setSelector(evaluate(action.getSelector(), context));
        action.setText(evaluate(action.getText(), context));
        action.setValue(evaluate(action.getValue(), context));
        
        ParentCriteria parentCriteria = action.getParent();
        if(parentCriteria != null){
            parentCriteria.setSelector(evaluate(parentCriteria.getSelector(), context));
            parentCriteria.setText(evaluate(parentCriteria.getText(), context));
            parentCriteria.setValue(evaluate(parentCriteria.getValue(), context));
        }
    }
}
