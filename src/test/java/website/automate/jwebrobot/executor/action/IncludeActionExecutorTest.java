/*
package website.automate.jwebrobot.executor.action;

import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;

import org.mockito.junit.MockitoJUnitRunner;
import website.automate.jwebrobot.context.GlobalExecutionContext;
import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.exceptions.ExceptionTranslator;
import website.automate.jwebrobot.exceptions.RecursiveScenarioInclusionException;
import website.automate.jwebrobot.executor.ScenarioExecutor;
import website.automate.jwebrobot.executor.ActionExecutorUtils;
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
    @Mock private ExpressionEvaluator expressionEvaluator;
    @Mock private ExecutionEventListeners listener;
    @Mock private ConditionalExpressionEvaluator conditionalExpressionEvaluator;
    @Mock private ExceptionTranslator translator;
    @Mock private ActionExecutorUtils actionExecutionUtils;

    private IncludeActionExecutor executor;

    @SuppressWarnings("unchecked")
    @Before
    public void init(){
        when(conditionalExpressionEvaluator.isExecutable(action, scenarioContext)).thenReturn(true);
        when(action.getScenarioByPath()).thenReturn(SCENARIO_TITLE);
        when(scenarioContext.getGlobalContext()).thenReturn(globalContext);
        when(globalContext.getScenarioByPath(SCENARIO_TITLE)).thenReturn(childScenario);
        when(scenarioContext.createChildContext(childScenario)).thenReturn(childScenarioContext);

        executor = new IncludeActionExecutor(expressionEvaluator, scenarioExecutor, listener,
                conditionalExpressionEvaluator, translator);
    }

    @Test
    public void includedScenarioShouldBeExecuted(){
        executor.execute(action, scenarioContext, actionExecutionUtils);
        
        verify(scenarioExecutor).runScenario(childScenario, childScenarioContext);
    }

    @Test(expected=RecursiveScenarioInclusionException.class)
    public void recursiveIncludedScenarioCausesException(){
        when(scenarioContext.containsScenario(childScenario)).thenReturn(true);
        when(translator.translate(Mockito.any(RuntimeException.class))).thenCallRealMethod();
        
        executor.execute(action, scenarioContext, actionExecutionUtils);
    }
}
*/
