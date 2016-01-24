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
import website.automate.jwebrobot.models.utils.PrecedenceComparator;

import javax.inject.Inject;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class ScenarioExecutor {

    @InjectLogger
    private Logger logger;

    private final PrecedenceComparator precedenceComparator;
    private final WebDriverProvider webDriverProvider;
    private final ActionExecutorFactory actionExecutorFactory;

    @Inject
    public ScenarioExecutor(
        PrecedenceComparator precedenceComparator,
        WebDriverProvider webDriverProvider,
        ActionExecutorFactory actionExecutorFactory
    ) {
        this.precedenceComparator = precedenceComparator;
        this.webDriverProvider = webDriverProvider;
        this.actionExecutorFactory = actionExecutorFactory;
    }

    public ExecutionResults execute(List<Scenario> scenarios, ContextHolder contextHolder, ExecutorOptions executorOptions) {
        Collections.sort(scenarios, precedenceComparator);

        ExecutionResults executionResults = new ExecutionResults();
        GlobalExecutionContext globalContext = new GlobalExecutionContext(scenarios);
        
        for (Scenario scenario : scenarios) {
            logger.info("Starting scenario {}...", scenario.getName());
            WebDriver driver = webDriverProvider.createInstance(executorOptions.getWebDriverType());

            ScenarioExecutionContext scenarioExecutionContext = new ScenarioExecutionContext(globalContext, scenario, driver, new HashMap<String, String>());
            try {
                runScenario(scenario, scenarioExecutionContext);
            } finally {
                driver.quit();
            }
            logger.info("Finished scenario {}.", scenario.getName());
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
