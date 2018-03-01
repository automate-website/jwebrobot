package website.automate.jwebrobot.executor.action;

import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.exceptions.ExceptionTranslator;
import website.automate.jwebrobot.expression.ConditionalExpressionEvaluator;
import website.automate.jwebrobot.expression.ExpressionEvaluator;
import website.automate.jwebrobot.listener.ExecutionEventListeners;
import website.automate.waml.io.model.action.DefineAction;
import website.automate.waml.io.model.criteria.DefineCriteria;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StoreActionExecutor extends ConditionalActionExecutor<DefineAction> {

	@Autowired
    public StoreActionExecutor(ExpressionEvaluator expressionEvaluator,
            ExecutionEventListeners listener,
            ConditionalExpressionEvaluator conditionalExpressionEvaluator,
            ExceptionTranslator exceptionTranslator) {
        super(expressionEvaluator, listener,
                conditionalExpressionEvaluator,
                exceptionTranslator);
    }

    @Override
    public void perform(DefineAction action, ScenarioExecutionContext context) {
        Map<String, Object> memory = context.getMemory();
        DefineCriteria criteria = action.getDefine();

        Map<String, Object> criteriaValueMap = criteria.getFacts();

        for(Entry<String, Object> criteriaValueEntry : criteriaValueMap.entrySet()){
            memory.put(criteriaValueEntry.getKey(), criteriaValueEntry.getValue());
        }
    }

    @Override
    public Class<DefineAction> getSupportedType() {
        return DefineAction.class;
    }

}
