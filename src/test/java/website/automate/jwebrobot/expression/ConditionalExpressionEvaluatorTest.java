package website.automate.jwebrobot.expression;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.listener.ExecutionEventListeners;
import website.automate.jwebrobot.model.Action;
import website.automate.jwebrobot.model.CriteriaType;
import website.automate.jwebrobot.model.CriteriaValue;

@RunWith(MockitoJUnitRunner.class)
public class ConditionalExpressionEvaluatorTest {

    @Mock private Action action;
    @Mock private CriteriaValue ifCriterion;
    @Mock private CriteriaValue unlessCriterion;
    @Mock private ScenarioExecutionContext context;
    @Mock private ExecutionEventListeners listener;
    @Mock private ConditionalExpressionEvaluator conditionalExpressionEvaluator;
    @Mock private ExpressionEvaluator expressionEvaluator;
    
    private static final String 
        TRUE_VALUE = "true",
        FALSE_VALUE = "false";
    
    private ConditionalExpressionEvaluator evaluator;
    
    @SuppressWarnings("unchecked")
    @Before
    public void init(){
        evaluator = new ConditionalExpressionEvaluator(expressionEvaluator);
        when(expressionEvaluator.evaluate(Mockito.eq(TRUE_VALUE), Mockito.anyMap())).thenReturn(true);
        when(expressionEvaluator.evaluate(Mockito.eq(FALSE_VALUE), Mockito.anyMap())).thenReturn(false);
    }
    
    
    @Test
    public void actionIsExecutedWhenNoConditionalsAreSet(){
        boolean executable = evaluator.isExecutable(action, context);
        
        assertThat(executable, is(true));
    }
    
    @Test
    public void actionIsExecutedWhenOnlyIfIsSetAndEvaluatesTrue(){
        when(action.getCriteria(CriteriaType.IF)).thenReturn(ifCriterion);
        when(ifCriterion.asString()).thenReturn(TRUE_VALUE);
        
        boolean executable = evaluator.isExecutable(action, context);
        
        assertThat(executable, is(true));
    }
    
    @Test
    public void actionIsNotExecutedWhenOnlyIfIsSetAndEvaluatesFalse(){
        when(action.getCriteria(CriteriaType.IF)).thenReturn(ifCriterion);
        when(ifCriterion.asString()).thenReturn(FALSE_VALUE);
        
        boolean executable = evaluator.isExecutable(action, context);
        
        assertThat(executable, is(false));
    }
    
    @Test
    public void actionIsExecutedWhenOnlyUnlessIsSetAndEvaluatesFalse(){
        when(action.getCriteria(CriteriaType.UNLESS)).thenReturn(unlessCriterion);
        when(unlessCriterion.asString()).thenReturn(FALSE_VALUE);
        
        boolean executable = evaluator.isExecutable(action, context);
        
        assertThat(executable, is(true));
    }
    
    @Test
    public void actionIsNotExecutedWhenOnlyUnlessIsSetAndEvaluatesTrue(){
        when(action.getCriteria(CriteriaType.UNLESS)).thenReturn(unlessCriterion);
        when(unlessCriterion.asString()).thenReturn(TRUE_VALUE);
        
        boolean executable = evaluator.isExecutable(action, context);
        
        assertThat(executable, is(false));
    }
    
    @Test
    public void actionIsExecutedWhenIfIsSetTrueAndUnlessIsSetFalse(){
        when(action.getCriteria(CriteriaType.IF)).thenReturn(ifCriterion);
        when(ifCriterion.asString()).thenReturn(TRUE_VALUE);
        when(action.getCriteria(CriteriaType.UNLESS)).thenReturn(unlessCriterion);
        when(unlessCriterion.asString()).thenReturn(FALSE_VALUE);
        
        boolean executable = evaluator.isExecutable(action, context);
        
        assertThat(executable, is(true));
    }
    
    @Test
    public void actionIsNotExecutedWhenIfIsSetFalseOrUnlessIsSetTrue(){
        when(action.getCriteria(CriteriaType.IF)).thenReturn(ifCriterion);
        when(ifCriterion.asString()).thenReturn(FALSE_VALUE);
        when(action.getCriteria(CriteriaType.UNLESS)).thenReturn(unlessCriterion);
        when(unlessCriterion.asString()).thenReturn(FALSE_VALUE);
        
        boolean executable = evaluator.isExecutable(action, context);
        
        assertThat(executable, is(false));
    }
}
