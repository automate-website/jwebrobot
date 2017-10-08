package website.automate.jwebrobot.executor.action;

import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.exceptions.ExceptionTranslator;
import website.automate.jwebrobot.expression.ConditionalExpressionEvaluator;
import website.automate.jwebrobot.expression.ExpressionEvaluator;
import website.automate.jwebrobot.listener.ExecutionEventListeners;
import website.automate.waml.io.model.action.StoreAction;

import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StoreActionExecutor extends ConditionalActionExecutor<StoreAction> {

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
    public void perform(StoreAction action, ScenarioExecutionContext context) {
        Map<String, Object> memory = context.getMemory();

        Map<String, String> criteriaValueMap = action.getFacts();

        for(Entry<String, String> criteriaValueEntry : criteriaValueMap.entrySet()){
            memory.put(criteriaValueEntry.getKey(), criteriaValueEntry.getValue());
        }
    }

    @Override
    public Class<StoreAction> getSupportedType() {
        return StoreAction.class;
    }

}
