package website.automate.jwebrobot.executor.action;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.inject.Provider;

import website.automate.jwebrobot.context.GlobalExecutionContext;
import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.executor.ScenarioExecutor;
import website.automate.jwebrobot.expression.ExpressionEvaluator;
import website.automate.jwebrobot.model.Action;
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
    
    private IncludeActionExecutor executor;
    
    @Test
    public void includedScenarioShouldBeExecuted(){
        when(action.getScenario()).thenReturn(SCENARIO_TITLE);
        when(scenarioContext.getGlobalContext()).thenReturn(globalContext);
        when(globalContext.getScenario(SCENARIO_TITLE)).thenReturn(childScenario);
        when(scenarioContext.createChildContext(childScenario)).thenReturn(childScenarioContext);
        when(scenarioExecutorProvider.get()).thenReturn(scenarioExecutor);
        executor = new IncludeActionExecutor(expressionEvaluator, scenarioExecutorProvider);
        
        executor.execute(action, scenarioContext);
        
        verify(scenarioExecutor).runScenario(childScenario, childScenarioContext);
    }
}
