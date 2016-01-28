package website.automate.jwebrobot.executor.action;

import com.google.inject.Inject;
import com.google.inject.Provider;

import website.automate.jwebrobot.context.GlobalExecutionContext;
import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.executor.ScenarioExecutor;
import website.automate.jwebrobot.expression.ExpressionEvaluator;
import website.automate.jwebrobot.model.Action;
import website.automate.jwebrobot.model.ActionType;
import website.automate.jwebrobot.model.Scenario;

public class IncludeActionExecutor extends ConditionalActionExecutor {

    private Provider<ScenarioExecutor> scenarioExecutor;
    
    @Inject
    public IncludeActionExecutor(ExpressionEvaluator expressionEvaluator, Provider<ScenarioExecutor> scenarioExecutor) {
        super(expressionEvaluator);
        this.scenarioExecutor = scenarioExecutor;
    }
    
    @Override
    public ActionType getActionType() {
        return ActionType.INCLUDE;
    }

    @Override
    public void perform(Action action, ScenarioExecutionContext context) {
        GlobalExecutionContext globalContext = context.getGlobalContext();
        String scenarioName = action.getScenario();
        Scenario scenario = globalContext.getScenario(scenarioName);
        ScenarioExecutionContext includedScenarioContext = context.createChildContext(scenario);
        scenarioExecutor.get().runScenario(scenario, includedScenarioContext);
    }

}
