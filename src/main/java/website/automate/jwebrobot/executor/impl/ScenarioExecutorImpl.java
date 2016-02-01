package website.automate.jwebrobot.executor.impl;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import website.automate.jwebrobot.config.logger.InjectLogger;
import website.automate.jwebrobot.context.GlobalExecutionContext;
import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.exceptions.StepsMustBePresentException;
import website.automate.jwebrobot.executor.ExecutorOptions;
import website.automate.jwebrobot.executor.ScenarioExecutor;
import website.automate.jwebrobot.executor.WebDriverProvider;
import website.automate.jwebrobot.executor.action.ActionExecutor;
import website.automate.jwebrobot.executor.action.ActionExecutorFactory;
import website.automate.jwebrobot.listener.ExecutionEventListeners;
import website.automate.jwebrobot.model.Action;
import website.automate.jwebrobot.model.Scenario;
import website.automate.jwebrobot.validator.ContextValidators;

import javax.inject.Inject;
import java.util.HashMap;

public class ScenarioExecutorImpl implements ScenarioExecutor {

    @InjectLogger
    private Logger logger;

    private final WebDriverProvider webDriverProvider;
    private final ActionExecutorFactory actionExecutorFactory;
    private final ExecutionEventListeners listener;
    private final ContextValidators validator;

    @Inject
    public ScenarioExecutorImpl(
        WebDriverProvider webDriverProvider,
        ActionExecutorFactory actionExecutorFactory,
        ExecutionEventListeners listener,
        ContextValidators validator
    ) {
        this.webDriverProvider = webDriverProvider;
        this.actionExecutorFactory = actionExecutorFactory;
        this.listener = listener;
        this.validator = validator;
    }

    @Override
    public void execute(GlobalExecutionContext context) {
        listener.beforeExecution(context);

        validator.validate(context);

        try{
            for (Scenario scenario : context.getScenarios()) {
                execute(context, scenario);
            }
        } catch (Exception e){
            listener.errorExecution(context, e);
            throw e;
        }

        listener.afterExecution(context);
    }

    private void execute(GlobalExecutionContext context, Scenario scenario){
        ExecutorOptions options = context.getOptions();

        if (!scenario.isFragment()){
            logger.info("Starting scenario {}...", scenario.getName());
            WebDriver driver = webDriverProvider.createInstance(options.getWebDriverType());

            ScenarioExecutionContext scenarioExecutionContext = new ScenarioExecutionContext(context, scenario, driver, new HashMap<String, Object>());
            try {
                listener.beforeScenario(scenarioExecutionContext);

                runScenario(scenario, scenarioExecutionContext);

                listener.afterScenario(scenarioExecutionContext);
            } catch (Exception e){
                listener.errorScenario(scenarioExecutionContext, e);
                throw e;
            }
            finally {
                driver.quit();
            }
            logger.info("Finished scenario {}.", scenario.getName());
        }
    }

    @Override
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
