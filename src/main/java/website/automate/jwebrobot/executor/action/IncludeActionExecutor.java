package website.automate.jwebrobot.executor.action;

import com.google.inject.Inject;
import com.google.inject.Provider;

import website.automate.jwebrobot.context.GlobalExecutionContext;
import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.executor.ScenarioExecutor;
import website.automate.jwebrobot.models.scenario.Scenario;
import website.automate.jwebrobot.models.scenario.actions.IncludeAction;

public class IncludeActionExecutor extends BaseActionExecutor<IncludeAction> {

    private Provider<ScenarioExecutor> scenarioExecutor;
    
    @Inject
    public IncludeActionExecutor(Provider<ScenarioExecutor> scenarioExecutor) {
        this.scenarioExecutor = scenarioExecutor;
    }
    
    @Override
    public Class<IncludeAction> getActionType() {
        return IncludeAction.class;
    }

    @Override
    public void safeExecute(IncludeAction action, ScenarioExecutionContext context) {
        GlobalExecutionContext globalContext = context.getGlobalContext();
        String scenarioName = action.getScenario().getValue();
        Scenario scenario = globalContext.getScenario(scenarioName);
        ScenarioExecutionContext includedScenarioContext = context.createChildContext(scenario);
        scenarioExecutor.get().runScenario(scenario, includedScenarioContext);
    }

}
