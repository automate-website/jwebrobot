package website.automate.jwebrobot.expression.action;

import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.expression.ExpressionEvaluator;
import website.automate.waml.io.model.action.FilterAction;
import website.automate.waml.io.model.criteria.FilterCriteria;

public abstract class FilterActionExpressionEvaluator<T extends FilterAction<?>> extends TimeLimitedActionExpressionEvaluator<T> {

    public FilterActionExpressionEvaluator(
            ExpressionEvaluator expressionEvaluator) {
        super(expressionEvaluator);
    }

    @Override
    public void evaluate(T action, ScenarioExecutionContext context) {
        super.evaluate(action, context);
        FilterCriteria criteria = action.getFilter();
        
        criteria.setSelector(evaluate(criteria.getSelector(), context));
        criteria.setText(evaluate(criteria.getText(), context));
        criteria.setValue(evaluate(criteria.getValue(), context));
        criteria.setElement(evaluate(criteria.getElement(), context));
        criteria.setParent(evaluate(criteria.getParent(), context));
    }
}
