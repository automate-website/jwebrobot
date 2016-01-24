package website.automate.jwebrobot.executor;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;

import website.automate.jwebrobot.config.logger.InjectLogger;
import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.context.GlobalExecutionContext;
import website.automate.jwebrobot.exceptions.StepsMustBePresentException;
import website.automate.jwebrobot.executor.action.ActionExecutor;
import website.automate.jwebrobot.executor.action.ActionExecutorFactory;
import website.automate.jwebrobot.models.scenario.Scenario;
import website.automate.jwebrobot.models.scenario.actions.Action;

import javax.inject.Inject;

import java.util.HashMap;
import java.util.List;

public class ScenarioExecutor {

    @InjectLogger
    private Logger logger;

    private final WebDriverProvider webDriverProvider;
    private final ActionExecutorFactory actionExecutorFactory;

    @Inject
    public ScenarioExecutor(
        WebDriverProvider webDriverProvider,
        ActionExecutorFactory actionExecutorFactory
    ) {
        this.webDriverProvider = webDriverProvider;
        this.actionExecutorFactory = actionExecutorFactory;
    }

    public ExecutionResults execute(GlobalExecutionContext context) {
        ExecutionResults executionResults = new ExecutionResults();
        List<Scenario> scenarios = context.getScenarios();
        ExecutorOptions options = context.getOptions();
        
        for (Scenario scenario : scenarios) {
            if (!scenario.isFragment()){
                logger.info("Starting scenario {}...", scenario.getName());
                WebDriver driver = webDriverProvider.createInstance(options.getWebDriverType());
    
                ScenarioExecutionContext scenarioExecutionContext = new ScenarioExecutionContext(context, scenario, driver, new HashMap<String, String>());
                try {
                    runScenario(scenario, scenarioExecutionContext);
                } finally {
                    driver.quit();
                }
                logger.info("Finished scenario {}.", scenario.getName());
            }
        }

        return executionResults;
    }


    public void runScenario(Scenario scenario, ScenarioExecutionContext scenarioExecutionContext) {
        if (scenario.getSteps() == null) {
            throw new StepsMustBePresentException(scenario.getName());
        }

        for (Action action : scenario.getSteps()) {
            ActionExecutor actionExecutor = actionExecutorFactory.getInstance(action.getClass());
            logger.debug("Executing {}", actionExecutor.getClass().getName());
            actionExecutor.execute(action, scenarioExecutionContext);
        }

    }
}
