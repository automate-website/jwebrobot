package website.automate.jwebrobot.expression.action;

import org.springframework.stereotype.Service;
import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.expression.ExpressionEvaluator;
import website.automate.waml.io.model.main.action.ExecuteAction;
import website.automate.waml.io.model.main.action.UriAction;
import website.automate.waml.io.model.main.criteria.ExecuteCriteria;
import website.automate.waml.io.model.main.criteria.UriCriteria;

@Service
public class UriActionExpressionEvaluator extends ConditionalActionExpressionEvaluator<UriAction> {

    public UriActionExpressionEvaluator(ExpressionEvaluator expressionEvaluator) {
        super(expressionEvaluator);
    }

    @Override
    public void evaluateTemplateAsString(UriAction action, ScenarioExecutionContext context) {
        super.evaluateTemplateAsString(action, context);

        UriCriteria criteria = action.getUri();

        criteria.setSrc(evaluateTemplateAsString(criteria.getSrc(), context));
        criteria.setBodyFormat(evaluateTemplateAsString(criteria.getBodyFormat(), context));
        criteria.setDest(evaluateTemplateAsString(criteria.getDest(), context));
        criteria.setMethod(evaluateTemplateAsString(criteria.getMethod(), context));
        criteria.setUrl(evaluateTemplateAsString(criteria.getUrl(), context));
        criteria.setPassword(evaluateTemplateAsString(criteria.getPassword(), context));
        criteria.setUser(evaluateTemplateAsString(criteria.getUser(), context));
        criteria.setStatusCode(evaluateTemplateAsString(criteria.getStatusCode(), context));
    }

    @Override
    public Class<UriAction> getSupportedType() {
        return UriAction.class;
    }
}
