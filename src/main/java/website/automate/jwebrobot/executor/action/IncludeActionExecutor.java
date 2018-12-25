package website.automate.jwebrobot.executor.action;

import org.springframework.stereotype.Service;
import website.automate.jwebrobot.context.GlobalExecutionContext;
import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.exceptions.RecursiveScenarioInclusionException;
import website.automate.jwebrobot.executor.ActionExecutorUtils;
import website.automate.jwebrobot.executor.ActionResult;
import website.automate.waml.io.model.main.Scenario;
import website.automate.waml.io.model.main.action.IncludeAction;

@Service
public class IncludeActionExecutor extends BaseActionExecutor<IncludeAction> {

    @Override
    public void execute(IncludeAction action,
                        ScenarioExecutionContext context,
                        ActionResult result,
                        ActionExecutorUtils utils) {
        GlobalExecutionContext globalContext = context.getGlobalContext();
        String scenarioName = action.getInclude().getScenario();
        Scenario scenario = globalContext.getScenario(scenarioName);
        
        if(context.containsScenario(scenario)){
            throw new RecursiveScenarioInclusionException(scenario.getName());
        }
        
        ScenarioExecutionContext includedScenarioContext = context.createChildContext(scenario);
        utils.getScenarioExecutor().runScenario(scenario, includedScenarioContext);
    }

    @Override
    public Class<IncludeAction> getSupportedType() {
        return IncludeAction.class;
    }
}
