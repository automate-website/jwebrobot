package website.automate.jwebrobot.executor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import website.automate.jwebrobot.executor.action.ActionEvaluator;
import website.automate.jwebrobot.executor.filter.ElementsFilter;
import website.automate.jwebrobot.expression.ConditionalExpressionEvaluator;
import website.automate.jwebrobot.expression.ExpressionEvaluator;
import website.automate.jwebrobot.listener.ExecutionEventListeners;

@Service
public class ActionExecutorUtils {

    private TimeoutResolver timeoutResolver;

    private ExpressionEvaluator expressionEvaluator;

    private ExecutionEventListeners listener;

    private ConditionalExpressionEvaluator conditionalExpressionEvaluator;

    private ElementsFilter elementsFilter;

    private ScenarioExecutor scenarioExecutor;

    private final ActionEvaluator actionEvaluator;

    private final WebDriverWaitProvider webdriverWaitProvider;

    @Autowired
    public ActionExecutorUtils(TimeoutResolver timeoutResolver,
                               ExpressionEvaluator expressionEvaluator,
                               ExecutionEventListeners listener,
                               ConditionalExpressionEvaluator conditionalExpressionEvaluator,
                               ElementsFilter elementsFilter,
                               @Lazy ScenarioExecutor scenarioExecutor,
                               ActionEvaluator actionEvaluator,
                               WebDriverWaitProvider webdriverWaitProvider){
        this.timeoutResolver = timeoutResolver;
        this.expressionEvaluator = expressionEvaluator;
        this.listener = listener;
        this.conditionalExpressionEvaluator = conditionalExpressionEvaluator;
        this.elementsFilter = elementsFilter;
        this.scenarioExecutor = scenarioExecutor;
        this.actionEvaluator = actionEvaluator;
        this.webdriverWaitProvider = webdriverWaitProvider;
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

    public ElementsFilter getElementsFilter() {
        return elementsFilter;
    }

    public ScenarioExecutor getScenarioExecutor() {
        return scenarioExecutor;
    }

    public ActionEvaluator getActionEvaluator() {
        return actionEvaluator;
    }

    public WebDriverWaitProvider getWebdriverWaitProvider() {
        return webdriverWaitProvider;
    }
}
