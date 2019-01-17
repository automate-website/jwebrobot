package website.automate.jwebrobot.executor;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import website.automate.jwebrobot.context.GlobalExecutionContext;
import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.exceptions.StepsMustBePresentException;
import website.automate.jwebrobot.executor.action.StepExecutor;
import website.automate.jwebrobot.listener.ExecutionEventListeners;
import website.automate.jwebrobot.validator.ContextValidators;
import website.automate.waml.io.model.main.Scenario;
import website.automate.waml.io.model.main.action.Action;

import static java.text.MessageFormat.format;

@Service
public class DefaultScenarioExecutor implements ScenarioExecutor {

    private static final String TEMPLATE_SCENARIO_START_OK_LOG_MESSAGE = "ok: {0} > {1}";

    private Logger logger = LoggerFactory.getLogger(DefaultScenarioExecutor.class);

    private final WebDriverProvider webDriverProvider;
    private final ExecutionEventListeners listener;
    private final ContextValidators validator;
    private final ScenarioPatternFilter scenarioPatternFilter;
    private final StepExecutor stepExecutor;

    @Autowired
    public DefaultScenarioExecutor(
        WebDriverProvider webDriverProvider,
        ExecutionEventListeners listener,
        ContextValidators validator,
        ScenarioPatternFilter scenarioPatternFilter,
        StepExecutor stepExecutor
    ) {
        this.webDriverProvider = webDriverProvider;
        this.listener = listener;
        this.validator = validator;
        this.scenarioPatternFilter = scenarioPatternFilter;
        this.stepExecutor = stepExecutor;
    }

    @Override
    public void execute(GlobalExecutionContext context) {
        listener.beforeExecution(context);
        validator.validate(context);

        try{
            for (Scenario scenario : context.getScenarios()) {
                if(scenario.isFragment()){
                    continue;
                }
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

        if (scenarioPatternFilter.isExecutable(options.getScenarioPattern(), scenario.getName())){
            logger.info(format(TEMPLATE_SCENARIO_START_OK_LOG_MESSAGE, scenario.getName(), "Start"));
            WebDriver driver = webDriverProvider.createInstance(options.getWebDriverType(), options.getWebDriverUrl());

            if(options.isMaximizeWindow() == Boolean.TRUE){
                driver.manage().window().maximize();
            }

            ScenarioExecutionContext scenarioExecutionContext = new ScenarioExecutionContext(context, scenario, driver);
            try {
                runScenario(scenario, scenarioExecutionContext);
            } catch (Exception e){
                listener.errorScenario(scenarioExecutionContext, e);
                throw e;
            }
            finally {
                driver.quit();
            }
            logger.info(format(TEMPLATE_SCENARIO_START_OK_LOG_MESSAGE, scenario.getName(), "End"));
        }
    }

    @Override
    public void runScenario(Scenario scenario, ScenarioExecutionContext scenarioExecutionContext) {
        listener.beforeScenario(scenarioExecutionContext);

        scenarioExecutionContext.setScenario(scenario);

        if (scenario.getSteps() == null) {
            throw new StepsMustBePresentException(scenario.getName());
        }

        for (Action action : scenario.getSteps()) {
            stepExecutor.execute(action, scenarioExecutionContext);
        }

        listener.afterScenario(scenarioExecutionContext);
    }
}
