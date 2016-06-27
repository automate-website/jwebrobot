package website.automate.jwebrobot.executor.action;

import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.inject.Provider;

import website.automate.jwebrobot.context.GlobalExecutionContext;
import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.exceptions.ExceptionTranslator;
import website.automate.jwebrobot.exceptions.RecursiveScenarioInclusionException;
import website.automate.jwebrobot.executor.ScenarioExecutor;
import website.automate.jwebrobot.expression.ConditionalExpressionEvaluator;
import website.automate.jwebrobot.expression.ExpressionEvaluator;
import website.automate.jwebrobot.listener.ExecutionEventListeners;
import website.automate.waml.io.model.Scenario;
import website.automate.waml.io.model.action.IncludeAction;

@RunWith(MockitoJUnitRunner.class)
public class IncludeActionExecutorTest<CriteriaValue> {

    private static final String SCENARIO_TITLE = "awesome scenario";

    @Mock private ScenarioExecutor scenarioExecutor;
    @Mock private IncludeAction action;
    @Mock private ScenarioExecutionContext scenarioContext;
    @Mock private ScenarioExecutionContext childScenarioContext;
    @Mock private GlobalExecutionContext globalContext;
    @Mock private Scenario childScenario;
    @Mock private CriteriaValue scenarioCriterion;
    @Mock private Provider<ScenarioExecutor> scenarioExecutorProvider;
    @Mock private ExpressionEvaluator expressionEvaluator;
    @Mock private ExecutionEventListeners listener;
    @Mock private ConditionalExpressionEvaluator conditionalExpressionEvaluator;
    @Mock private ExceptionTranslator translator;

    private IncludeActionExecutor executor;

    @SuppressWarnings("unchecked")
    @Before
    public void init(){
        when(conditionalExpressionEvaluator.isExecutable(action, scenarioContext)).thenReturn(true);
        when(action.getScenario()).thenReturn(SCENARIO_TITLE);
        when(scenarioContext.getGlobalContext()).thenReturn(globalContext);
        when(globalContext.getScenario(SCENARIO_TITLE)).thenReturn(childScenario);
        when(scenarioContext.createChildContext(childScenario)).thenReturn(childScenarioContext);
        when(scenarioExecutorProvider.get()).thenReturn(scenarioExecutor);
        when(expressionEvaluator.evaluate(eq(SCENARIO_TITLE), anyMap())).thenReturn(SCENARIO_TITLE);
        
        executor = new IncludeActionExecutor(expressionEvaluator, scenarioExecutorProvider, listener,
                conditionalExpressionEvaluator, translator);
    }

    @Test
    public void includedScenarioShouldBeExecuted(){
        executor.execute(action, scenarioContext);
        
        verify(scenarioExecutor).runScenario(childScenario, childScenarioContext);
    }

    @Test(expected=RecursiveScenarioInclusionException.class)
    public void recursiveIncludedScenarioCausesException(){
        when(scenarioContext.containsScenario(childScenario)).thenReturn(true);
        
        executor.execute(action, scenarioContext);
    }
}
