package website.automate.jwebrobot.executor;

import website.automate.jwebrobot.context.GlobalExecutionContext;
import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.waml.io.model.main.Scenario;

public interface ScenarioExecutor {

    void execute(GlobalExecutionContext context);

    void runScenario(Scenario scenario, ScenarioExecutionContext scenarioExecutionContext);
}
