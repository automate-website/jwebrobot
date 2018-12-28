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
    public void evaluateTemplateAsString(T action, ScenarioExecutionContext context) {
        super.evaluateTemplateAsString(action, context);
        FilterCriteria filterCriteria = action.getFilter();

        filterCriteria.setSelector(evaluateTemplateAsString(filterCriteria.getSelector(), context));
        filterCriteria.setText(evaluateTemplateAsString(filterCriteria.getText(), context));
        filterCriteria.setValue(evaluateTemplateAsString(filterCriteria.getValue(), context));
        filterCriteria.setParent(evaluateTemplateAsString(filterCriteria.getParent(), context));
    }
}
