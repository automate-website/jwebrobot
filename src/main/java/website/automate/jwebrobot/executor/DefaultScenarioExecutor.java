package website.automate.jwebrobot.executor;

import org.openqa.selenium.WebDriver;
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

@Service
public class DefaultScenarioExecutor implements ScenarioExecutor {

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
