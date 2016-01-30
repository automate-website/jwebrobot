package website.automate.jwebrobot.executor;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;

import website.automate.jwebrobot.config.logger.InjectLogger;
import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.context.GlobalExecutionContext;
import website.automate.jwebrobot.exceptions.StepsMustBePresentException;
import website.automate.jwebrobot.executor.action.ActionExecutor;
import website.automate.jwebrobot.executor.action.ActionExecutorFactory;
import website.automate.jwebrobot.listener.ExecutionEventListeners;
import website.automate.jwebrobot.model.Action;
import website.automate.jwebrobot.model.Scenario;

import javax.inject.Inject;

import java.util.HashMap;
import java.util.List;

public class ScenarioExecutor {

    @InjectLogger
    private Logger logger;

    private final WebDriverProvider webDriverProvider;
    private final ActionExecutorFactory actionExecutorFactory;
    private final ExecutionEventListeners listener;

    @Inject
    public ScenarioExecutor(
        WebDriverProvider webDriverProvider,
        ActionExecutorFactory actionExecutorFactory,
        ExecutionEventListeners listener
    ) {
        this.webDriverProvider = webDriverProvider;
        this.actionExecutorFactory = actionExecutorFactory;
        this.listener = listener;
    }

    public ExecutionResults execute(GlobalExecutionContext context) {
        ExecutionResults executionResults = new ExecutionResults();
        List<Scenario> scenarios = context.getScenarios();
        ExecutorOptions options = context.getOptions();
        
        for (Scenario scenario : scenarios) {
            if (!scenario.isFragment()){
                logger.info("Starting scenario {}...", scenario.getName());
                WebDriver driver = webDriverProvider.createInstance(options.getWebDriverType());
    
                ScenarioExecutionContext scenarioExecutionContext = new ScenarioExecutionContext(context, scenario, driver, new HashMap<String, Object>());
                try {
                    listener.beforeScenario(scenarioExecutionContext);
                    
                    runScenario(scenario, scenarioExecutionContext);
                    
                    listener.afterScenario(scenarioExecutionContext);
                } finally {
                    listener.errorScenario(scenarioExecutionContext);
                    
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
            ActionExecutor actionExecutor = actionExecutorFactory.getInstance(action.getType());
            logger.debug("Executing {}", actionExecutor.getClass().getName());
            actionExecutor.execute(action, scenarioExecutionContext);
        }

    }
}
