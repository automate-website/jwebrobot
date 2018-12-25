package website.automate.jwebrobot.executor;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import website.automate.jwebrobot.context.GlobalExecutionContext;
import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.exceptions.StepsMustBePresentException;
import website.automate.jwebrobot.executor.action.ActionExecutor;
import website.automate.jwebrobot.executor.action.ActionExecutorFactory;
import website.automate.jwebrobot.executor.action.ActionPreprocessor;
import website.automate.jwebrobot.expression.ConditionalExpressionEvaluator;
import website.automate.jwebrobot.listener.ExecutionEventListeners;
import website.automate.jwebrobot.mapper.action.AbstractActionMapper;
import website.automate.jwebrobot.utils.SimpleNoNullValueStyle;
import website.automate.jwebrobot.validator.ContextValidators;
import website.automate.waml.io.model.main.Scenario;
import website.automate.waml.io.model.main.action.Action;

@Service
public class DefaultScenarioExecutor implements ScenarioExecutor {

    private Logger logger = LoggerFactory.getLogger(DefaultScenarioExecutor.class);

    private final WebDriverProvider webDriverProvider;
    private final ActionExecutorFactory actionExecutorFactory;
    private final ExecutionEventListeners listener;
    private final ContextValidators validator;
    private final ConditionalExpressionEvaluator conditionalExpressionEvaluator;
    private final ActionPreprocessor actionPreprocessor;
    private final AbstractActionMapper abstractActionMapper;
    private final ScenarioPatternFilter scenarioPatternFilter;
    private final ActionExecutorUtils executionUtils;

    @Autowired
    public DefaultScenarioExecutor(
        WebDriverProvider webDriverProvider,
        ActionExecutorFactory actionExecutorFactory,
        ExecutionEventListeners listener,
        ContextValidators validator,
        ConditionalExpressionEvaluator conditionalExpressionEvaluator,
        ActionPreprocessor actionPreprocessor,
        AbstractActionMapper abstractActionMapper,
        ScenarioPatternFilter scenarioPatternFilter,
        ActionExecutorUtils executionUtils
    ) {
        this.webDriverProvider = webDriverProvider;
        this.actionExecutorFactory = actionExecutorFactory;
        this.listener = listener;
        this.validator = validator;
        this.conditionalExpressionEvaluator = conditionalExpressionEvaluator;
        this.actionPreprocessor = actionPreprocessor;
        this.abstractActionMapper = abstractActionMapper;
        this.scenarioPatternFilter = scenarioPatternFilter;
        this.executionUtils = executionUtils;
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
            logger.info(scenario.getName() + " > Start");
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
            logger.info(scenario.getName() + " > End");
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
            ActionExecutor<Action> actionExecutor = actionExecutorFactory.getInstance(action.getClass());

            Action preprocessedAction = actionPreprocessor.preprocess(abstractActionMapper.map(action), scenarioExecutionContext);

            logger.info(getActionLogMessage(scenario, preprocessedAction));
            actionExecutor.execute(preprocessedAction, scenarioExecutionContext, executionUtils);
        }

        listener.afterScenario(scenarioExecutionContext);
    }

    private String getActionLogMessage(Scenario scenario, Action action){
        return scenario.getName() + " > " + getActionName(action) + getActionValue(action);
    }

    private String getActionName(Action action){
        return action.getClass().getSimpleName().replaceFirst("Action", "");
    }

    private String getActionValue(Action action){
        return ReflectionToStringBuilder.toString(action, SimpleNoNullValueStyle.INSTANCE);
    }
}
