package website.automate.jwebrobot.executor.action;

import java.util.List;

import org.openqa.selenium.WebElement;

import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.exceptions.ExceptionTranslator;
import website.automate.jwebrobot.executor.filter.ElementFilterChain;
import website.automate.jwebrobot.expression.ConditionalExpressionEvaluator;
import website.automate.jwebrobot.expression.ExpressionEvaluator;
import website.automate.jwebrobot.listener.ExecutionEventListeners;
import website.automate.waml.io.model.action.FilterAction;

public abstract class FilterActionExecutor<T extends FilterAction> extends ConditionalActionExecutor<T> {

    private ElementFilterChain elementFilterChain;
    
    public FilterActionExecutor(ExpressionEvaluator expressionEvaluator,
            ExecutionEventListeners listener, ElementFilterChain elementFilterChain,
            ConditionalExpressionEvaluator conditionalExpressionEvaluator,
            ExceptionTranslator exceptionTranslator) {
        super(expressionEvaluator, listener,
                conditionalExpressionEvaluator,
                exceptionTranslator);
        this.elementFilterChain = elementFilterChain;
    }
    
    protected WebElement filter(ScenarioExecutionContext context, T action){
        return getFirstOrNull(elementFilterChain.filter(context, action));
    }

    private WebElement getFirstOrNull(List<WebElement> webElements){
        if(webElements.isEmpty()){
            return null;
        }
        return webElements.get(0);
    }
}
