package website.automate.jwebrobot.expression.action;

import org.springframework.stereotype.Service;
import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.expression.ExpressionEvaluator;
import website.automate.jwebrobot.expression.ObjectEvaluator;
import website.automate.waml.io.model.main.action.UriAction;
import website.automate.waml.io.model.main.criteria.UriCriteria;

@Service
public class UriActionExpressionEvaluator extends ConditionalActionExpressionEvaluator<UriAction> {

    private ObjectEvaluator objectEvaluator;

    public UriActionExpressionEvaluator(ExpressionEvaluator expressionEvaluator,
                                        ObjectEvaluator objectEvaluator) {
        super(expressionEvaluator);
        this.objectEvaluator = objectEvaluator;
    }

    @Override
    public void evaluate(UriAction action, ScenarioExecutionContext context) {
        super.evaluate(action, context);

        UriCriteria criteria = action.getUri();

        criteria.setSrc(evaluateAsString(criteria.getSrc(), context));
        criteria.setBodyFormat(evaluateAsString(criteria.getBodyFormat(), context));
        criteria.setDest(evaluateAsString(criteria.getDest(), context));
        criteria.setMethod(evaluateAsString(criteria.getMethod(), context));
        criteria.setUrl(evaluateAsString(criteria.getUrl(), context));
        criteria.setPassword(evaluateAsString(criteria.getPassword(), context));
        criteria.setUser(evaluateAsString(criteria.getUser(), context));
        criteria.setStatusCode(evaluateAsString(criteria.getStatusCode(), context));

        criteria.setBody(objectEvaluator.evaluate(criteria.getBody(), context.getTotalMemory(), Object.class));
        criteria.setHeaders(objectEvaluator.evaluate(criteria.getHeaders(), context.getTotalMemory(), Object.class));
    }

    @Override
    public Class<UriAction> getSupportedType() {
        return UriAction.class;
    }
}
