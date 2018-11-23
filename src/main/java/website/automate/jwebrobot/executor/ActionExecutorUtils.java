package website.automate.jwebrobot.executor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import website.automate.jwebrobot.exceptions.ExceptionTranslator;
import website.automate.jwebrobot.executor.filter.ElementFilterChain;
import website.automate.jwebrobot.executor.filter.ElementsFilter;
import website.automate.jwebrobot.expression.ConditionalExpressionEvaluator;
import website.automate.jwebrobot.expression.ExpressionEvaluator;
import website.automate.jwebrobot.listener.ExecutionEventListeners;

@Service
public class ActionExecutorUtils {

    private TimeoutResolver timeoutResolver;

    private  ExpressionEvaluator expressionEvaluator;

    private ExecutionEventListeners listener;

    private ConditionalExpressionEvaluator conditionalExpressionEvaluator;

    private ExceptionTranslator exceptionTranslator;

    private ElementsFilter elementsFilter;

    private WebElementProvider webElementProvider;

    private ScenarioExecutor scenarioExecutor;

    @Autowired
    public ActionExecutorUtils(TimeoutResolver timeoutResolver,
                               ExpressionEvaluator expressionEvaluator,
                               ExecutionEventListeners listener,
                               ConditionalExpressionEvaluator conditionalExpressionEvaluator,
                               ExceptionTranslator exceptionTranslator,
                               ElementFilterChain elementFilterChain,
                               ElementsFilter elementsFilter,
                               ElementStorage elementStorage,
                               WebElementProvider webElementProvider,
                               @Lazy ScenarioExecutor scenarioExecutor){
        this.timeoutResolver = timeoutResolver;
        this.expressionEvaluator = expressionEvaluator;
        this.listener = listener;
        this.conditionalExpressionEvaluator = conditionalExpressionEvaluator;
        this.exceptionTranslator = exceptionTranslator;
        this.elementsFilter = elementsFilter;
        this.elementStorage = elementStorage;
        this.webElementProvider = webElementProvider;
        this.scenarioExecutor = scenarioExecutor;
    }

    public TimeoutResolver getTimeoutResolver() {
        return timeoutResolver;
    }

    public ExpressionEvaluator getExpressionEvaluator() {
        return expressionEvaluator;
    }

    public ExecutionEventListeners getListener() {
        return listener;
    }

    public ConditionalExpressionEvaluator getConditionalExpressionEvaluator() {
        return conditionalExpressionEvaluator;
    }

    public ExceptionTranslator getExceptionTranslator() {
        return exceptionTranslator;
    }

    public ElementsFilter getElementsFilter() {
        return elementsFilter;
    }

    public WebElementProvider getWebElementProvider() {
        return webElementProvider;
    }

    public ScenarioExecutor getScenarioExecutor() {
        return scenarioExecutor;
    }
}
