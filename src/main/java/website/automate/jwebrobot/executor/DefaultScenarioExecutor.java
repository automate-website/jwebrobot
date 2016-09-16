package website.automate.jwebrobot.executor;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;

import website.automate.jwebrobot.config.logger.InjectLogger;
import website.automate.jwebrobot.context.GlobalExecutionContext;
import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.exceptions.StepsMustBePresentException;
import website.automate.jwebrobot.executor.action.ActionExecutor;
import website.automate.jwebrobot.executor.action.ActionExecutorFactory;
import website.automate.jwebrobot.executor.action.ActionPreprocessor;
import website.automate.jwebrobot.expression.ConditionalExpressionEvaluator;
import website.automate.jwebrobot.listener.ExecutionEventListeners;
import website.automate.jwebrobot.mapper.action.AbstractActionMapper;
import website.automate.jwebrobot.validator.ContextValidators;
import website.automate.waml.io.model.Scenario;
import website.automate.waml.io.model.action.Action;

import javax.inject.Inject;

public class DefaultScenarioExecutor implements ScenarioExecutor {

    @InjectLogger
    private Logger logger;

    private final WebDriverProvider webDriverProvider;
    private final ActionExecutorFactory actionExecutorFactory;
    private final ExecutionEventListeners listener;
    private final ContextValidators validator;
    private final ConditionalExpressionEvaluator conditionalExpressionEvaluator;
    private final ScenarioPreprocessor scenarioPreprocessor;
    private final ActionPreprocessor actionPreprocessor;
    private final AbstractActionMapper abstractActionMapper;
    private final ScenarioPatternFilter scenarioPatternFilter;
    
    @Inject
    public DefaultScenarioExecutor(
        WebDriverProvider webDriverProvider,
        ActionExecutorFactory actionExecutorFactory,
        ExecutionEventListeners listener,
        ContextValidators validator,
        ConditionalExpressionEvaluator conditionalExpressionEvaluator,
        ScenarioPreprocessor scenarioPreprocessor,
        ActionPreprocessor actionPreprocessor,
        AbstractActionMapper abstractActionMapper,
        ScenarioPatternFilter scenarioPatternFilter
    ) {
        this.webDriverProvider = webDriverProvider;
        this.actionExecutorFactory = actionExecutorFactory;
        this.listener = listener;
        this.validator = validator;
        this.conditionalExpressionEvaluator = conditionalExpressionEvaluator;
        this.scenarioPreprocessor = scenarioPreprocessor;
        this.actionPreprocessor = actionPreprocessor;
        this.abstractActionMapper = abstractActionMapper;
        this.scenarioPatternFilter =scenarioPatternFilter;
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

        if (!scenario.getFragment() && scenarioPatternFilter.isExecutable(options.getScenarioPattern(), scenario.getName())){
            logger.info("Starting scenario {}...", scenario.getName());
            WebDriver driver = webDriverProvider.createInstance(options.getWebDriverType());
            
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
            logger.info("Finished scenario {}.", scenario.getName());
        }
    }

    @Override
    public void runScenario(Scenario scenario, ScenarioExecutionContext scenarioExecutionContext) {
        boolean executable = conditionalExpressionEvaluator.isExecutable(scenario, scenarioExecutionContext);
        
        if(!executable){
            return;
        }
        
        listener.beforeScenario(scenarioExecutionContext);
        
        Scenario preprocessedScenario = scenarioPreprocessor.preprocess(scenario, scenarioExecutionContext);
        scenarioExecutionContext.setScenario(preprocessedScenario);
        
        if (scenario.getSteps() == null) {
            throw new StepsMustBePresentException(scenario.getName());
        }

        for (Action action : scenario.getSteps()) {
            ActionExecutor<Action> actionExecutor = actionExecutorFactory.getInstance(action.getClass());
            
            Action preprocessedAction = actionPreprocessor.preprocess(abstractActionMapper.map(action), scenarioExecutionContext);
            
            logger.debug("Executing {}", actionExecutor.getClass().getName());
            actionExecutor.execute(preprocessedAction, scenarioExecutionContext);
        }
        
        listener.afterScenario(scenarioExecutionContext);
    }
}
