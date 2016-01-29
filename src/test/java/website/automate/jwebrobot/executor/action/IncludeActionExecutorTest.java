package website.automate.jwebrobot.executor.action;

import static java.util.Collections.singletonMap;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.inject.Provider;

import website.automate.jwebrobot.context.GlobalExecutionContext;
import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.exceptions.RecursiveScenarioInclusionException;
import website.automate.jwebrobot.executor.ScenarioExecutor;
import website.automate.jwebrobot.expression.ExpressionEvaluator;
import website.automate.jwebrobot.model.Action;
import website.automate.jwebrobot.model.ActionType;
import website.automate.jwebrobot.model.CriteriaType;
import website.automate.jwebrobot.model.CriteriaValue;
import website.automate.jwebrobot.model.Scenario;

@RunWith(MockitoJUnitRunner.class)
public class IncludeActionExecutorTest {

    private static final String SCENARIO_TITLE = "awesome scenario";

    @Mock private ScenarioExecutor scenarioExecutor;
    @Mock private Action action;
    @Mock private ScenarioExecutionContext scenarioContext;
    @Mock private ScenarioExecutionContext childScenarioContext;
    @Mock private GlobalExecutionContext globalContext;
    @Mock private Scenario childScenario;
    @Mock private CriteriaValue scenarioCriterion;
    @Mock private Provider<ScenarioExecutor> scenarioExecutorProvider;
    @Mock private ExpressionEvaluator expressionEvaluator;

    private static final ActionType ACTION_TYPE = ActionType.INCLUDE;
    private static final CriteriaType CRITERIA_TYPE = CriteriaType.SCENARIO;

    private IncludeActionExecutor executor;

    @SuppressWarnings("unchecked")
    @Before
    public void init(){
        when(action.getScenario()).thenReturn(SCENARIO_TITLE);
        when(action.getType()).thenReturn(ACTION_TYPE);
        when(action.getCriteriaValueMap()).thenReturn(singletonMap(CRITERIA_TYPE.getName(), scenarioCriterion));
        when(scenarioCriterion.asString()).thenReturn(SCENARIO_TITLE);
        when(scenarioContext.getGlobalContext()).thenReturn(globalContext);
        when(globalContext.getScenario(SCENARIO_TITLE)).thenReturn(childScenario);
        when(scenarioContext.createChildContext(childScenario)).thenReturn(childScenarioContext);
        when(scenarioExecutorProvider.get()).thenReturn(scenarioExecutor);
        when(expressionEvaluator.evaluate(eq(SCENARIO_TITLE), anyMap())).thenReturn(SCENARIO_TITLE);
        
        executor = new IncludeActionExecutor(expressionEvaluator, scenarioExecutorProvider);
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
