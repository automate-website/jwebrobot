package website.automate.jwebrobot.expression.action;

import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.expression.ExpressionEvaluator;
import website.automate.waml.io.model.action.DefineAction;
import website.automate.waml.io.model.criteria.DefineCriteria;
import java.util.Map;
import java.util.Map.Entry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefineActionExpressionEvaluator extends ConditionalActionExpressionEvaluator<DefineAction> {

    @Autowired
    public DefineActionExpressionEvaluator(
            ExpressionEvaluator expressionEvaluator) {
        super(expressionEvaluator);
    }

    @Override
    public void evaluate(DefineAction action, ScenarioExecutionContext context) {
        DefineCriteria criteria = action.getDefine();
        Map<String, Object> value = criteria.getFacts();
        for(Entry<String, Object> valueEntry : value.entrySet()){
            value.put(valueEntry.getKey(), evaluate(valueEntry.getValue().toString(), context));
        }
    }

    @Override
    public Class<DefineAction> getSupportedType() {
        return DefineAction.class;
    }
}
