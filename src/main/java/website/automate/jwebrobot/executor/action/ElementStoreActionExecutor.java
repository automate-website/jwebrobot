package website.automate.jwebrobot.executor.action;

import static org.apache.commons.lang.StringUtils.isNotBlank;

import java.util.Map;

import org.openqa.selenium.WebElement;

import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.exceptions.ExceptionTranslator;
import website.automate.jwebrobot.executor.filter.ElementFilterChain;
import website.automate.jwebrobot.expression.ConditionalExpressionEvaluator;
import website.automate.jwebrobot.expression.ExpressionEvaluator;
import website.automate.jwebrobot.listener.ExecutionEventListeners;
import website.automate.waml.io.model.action.ElementStoreAction;

public abstract class ElementStoreActionExecutor<T extends ElementStoreAction> extends FilterActionExecutor<T> {

    public ElementStoreActionExecutor(ExpressionEvaluator expressionEvaluator, ExecutionEventListeners listener,
            ElementFilterChain elementFilterChain, ConditionalExpressionEvaluator conditionalExpressionEvaluator,
            ExceptionTranslator exceptionTranslator) {
        super(expressionEvaluator, listener, 
                elementFilterChain, conditionalExpressionEvaluator, 
                exceptionTranslator);
    }

    @Override
    protected WebElement filter(ScenarioExecutionContext context, T action){
        WebElement webElement = super.filter(context, action);
        String storeKey = action.getStore();
        
        if(isNotBlank(storeKey) && webElement != null){
            Map<String, Object> memory = context.getMemory();
            memory.put(storeKey, webElement);
        }
        
        return webElement;
    }
}
