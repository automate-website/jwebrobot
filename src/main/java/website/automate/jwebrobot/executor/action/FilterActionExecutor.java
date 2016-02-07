package website.automate.jwebrobot.executor.action;

import java.util.List;

import org.openqa.selenium.WebElement;

import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.executor.filter.ElementFilterChain;
import website.automate.jwebrobot.expression.ConditionalExpressionEvaluator;
import website.automate.jwebrobot.expression.ExpressionEvaluator;
import website.automate.jwebrobot.listener.ExecutionEventListeners;
import website.automate.jwebrobot.model.Action;

public abstract class FilterActionExecutor extends EvaluatedActionExecutor {

    private ElementFilterChain elementFilterChain;
    
    public FilterActionExecutor(ExpressionEvaluator expressionEvaluator,
            ExecutionEventListeners listener, ElementFilterChain elementFilterChain,
            ConditionalExpressionEvaluator conditionalExpressionEvaluator) {
        super(expressionEvaluator, listener,
                conditionalExpressionEvaluator);
        this.elementFilterChain = elementFilterChain;
    }
    
    protected WebElement filter(ScenarioExecutionContext context, Action action){
        return getFirstOrNull(elementFilterChain.filter(context, action));
    }

    private WebElement getFirstOrNull(List<WebElement> webElements){
        if(webElements.isEmpty()){
            return null;
        }
        return webElements.get(0);
    }
}
