package website.automate.jwebrobot.executor.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import website.automate.jwebrobot.context.GlobalExecutionContext;
import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.exceptions.ExceptionTranslator;
import website.automate.jwebrobot.exceptions.RecursiveScenarioInclusionException;
import website.automate.jwebrobot.executor.ScenarioExecutor;
import website.automate.jwebrobot.expression.ConditionalExpressionEvaluator;
import website.automate.jwebrobot.expression.ExpressionEvaluator;
import website.automate.jwebrobot.listener.ExecutionEventListeners;
import website.automate.waml.io.model.Scenario;
import website.automate.waml.io.model.action.IncludeAction;
import website.automate.waml.io.model.criteria.IncludeCriteria;

@Service
public class IncludeActionExecutor extends ConditionalActionExecutor<IncludeAction> {

    private ScenarioExecutor scenarioExecutor;
    
    @Autowired
    @Lazy
    public IncludeActionExecutor(ExpressionEvaluator expressionEvaluator, ScenarioExecutor scenarioExecutor,
            ExecutionEventListeners listener, ConditionalExpressionEvaluator conditionalExpressionEvaluator,
            ExceptionTranslator exceptionTranslator) {
        super(expressionEvaluator, listener, conditionalExpressionEvaluator, exceptionTranslator);
        this.scenarioExecutor = scenarioExecutor;
    }
    
    @Override
    public void perform(IncludeAction action, ScenarioExecutionContext context) {
        GlobalExecutionContext globalContext = context.getGlobalContext();
        IncludeCriteria criteria = action.getInclude();

        String scenarioName = criteria.getScenario();
        Scenario scenario = globalContext.getScenario(scenarioName);
        
        if(context.containsScenario(scenario)){
            throw new RecursiveScenarioInclusionException(scenario.getName());
        }
        
        ScenarioExecutionContext includedScenarioContext = context.createChildContext(scenario);
        scenarioExecutor.runScenario(scenario, includedScenarioContext);
    }

    @Override
    public Class<IncludeAction> getSupportedType() {
        return IncludeAction.class;
    }

}
