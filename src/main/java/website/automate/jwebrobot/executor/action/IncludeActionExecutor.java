package website.automate.jwebrobot.executor.action;

import org.springframework.stereotype.Service;

import website.automate.jwebrobot.context.GlobalExecutionContext;
import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.exceptions.RecursiveScenarioInclusionException;
import website.automate.jwebrobot.executor.ActionExecutorUtils;
import website.automate.jwebrobot.executor.ActionResult;
import website.automate.waml.io.model.Scenario;
import website.automate.waml.io.model.action.IncludeAction;

@Service
public class IncludeActionExecutor implements ActionExecutor<IncludeAction> {

    @Override
    public ActionResult execute(final IncludeAction action,
                                final ScenarioExecutionContext context,
                                final ActionExecutorUtils utils) {
        GlobalExecutionContext globalContext = context.getGlobalContext();
        String scenarioName = action.getScenario();
        Scenario scenario = globalContext.getScenario(scenarioName);
        
        if(context.containsScenario(scenario)){
            throw new RecursiveScenarioInclusionException(scenario.getName());
        }
        
        ScenarioExecutionContext includedScenarioContext = context.createChildContext(scenario);
        utils.getScenarioExecutor().runScenario(scenario, includedScenarioContext);

        return null;
    }

    @Override
    public Class<IncludeAction> getSupportedType() {
        return IncludeAction.class;
    }
}
