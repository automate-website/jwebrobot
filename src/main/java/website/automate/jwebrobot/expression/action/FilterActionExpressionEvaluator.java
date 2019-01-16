package website.automate.jwebrobot.expression.action;

import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.expression.ExpressionEvaluator;
import website.automate.waml.io.model.main.action.FilterAction;
import website.automate.waml.io.model.main.criteria.FilterCriteria;

public abstract class FilterActionExpressionEvaluator<T extends FilterAction> extends TimeLimitedActionExpressionEvaluator<T> {

    public FilterActionExpressionEvaluator(
            ExpressionEvaluator expressionEvaluator) {
        super(expressionEvaluator);
    }

    @Override
    public void evaluate(T action, ScenarioExecutionContext context) {
        super.evaluate(action, context);
        FilterCriteria filterCriteria = action.getFilter();

        filterCriteria.setSelector(evaluateAsString(filterCriteria.getSelector(), context));
        filterCriteria.setText(evaluateAsString(filterCriteria.getText(), context));
        filterCriteria.setValue(evaluateAsString(filterCriteria.getValue(), context));
        filterCriteria.setParent(evaluateAsString(filterCriteria.getParent(), context));
    }
}
