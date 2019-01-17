package website.automate.jwebrobot.expression;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.waml.io.model.main.action.ConditionalAction;

import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ConditionalExpressionEvaluatorTest {

    @Mock private ConditionalAction action;
    @Mock private ScenarioExecutionContext context;
    @Mock private ExpressionEvaluator expressionEvaluator;
    @Mock private Map<String, Object> memory;

    private static final String
        TRUE_VALUE = "true",
        FALSE_VALUE = "false";

    @InjectMocks
    private ConditionalExpressionEvaluator evaluator;
    
    @Before
    public void init(){
        when(context.getTotalMemory()).thenReturn(memory);
        when(expressionEvaluator.evaluate(TRUE_VALUE, memory, Boolean.class)).thenReturn(true);
        when(expressionEvaluator.evaluate(FALSE_VALUE, memory, Boolean.class)).thenReturn(false);
    }
    
    @Test
    public void actionIsExecutableWhenNoConditionalsAreSet(){
        boolean executable = evaluator.isExecutable(action, context);
        
        assertThat(executable, is(true));
    }
    
    @Test
    public void actionIsExecutableWhenOnlyIfIsSetAndEvaluatesTrue(){
        when(action.getWhen()).thenReturn(TRUE_VALUE);
        
        boolean executable = evaluator.isExecutable(action, context);
        
        assertThat(executable, is(true));
    }
    
    @Test
    public void actionIsNotExecutableWhenOnlyIfIsSetAndEvaluatesFalse(){
        when(action.getWhen()).thenReturn(FALSE_VALUE);
        
        boolean executable = evaluator.isExecutable(action, context);
        
        assertThat(executable, is(false));
    }
    
    @Test
    public void actionIsExecutableWhenOnlyUnlessIsSetAndEvaluatesFalse(){
        when(action.getUnless()).thenReturn(FALSE_VALUE);
        
        boolean executable = evaluator.isExecutable(action, context);
        
        assertThat(executable, is(true));
    }
    
    @Test
    public void actionIsNotExecutableWhenOnlyUnlessIsSetAndEvaluatesTrue(){
        when(action.getUnless()).thenReturn(TRUE_VALUE);
        
        boolean executable = evaluator.isExecutable(action, context);
        
        assertThat(executable, is(false));
    }
    
    @Test
    public void actionIsExecutableWhenIfIsSetTrueAndUnlessIsSetFalse(){
        when(action.getWhen()).thenReturn(TRUE_VALUE);
        when(action.getUnless()).thenReturn(FALSE_VALUE);
        
        boolean executable = evaluator.isExecutable(action, context);
        
        assertThat(executable, is(true));
    }
    
    @Test
    public void actionIsNotExecutableWhenIfIsSetFalseOrUnlessIsSetTrue(){
        when(action.getWhen()).thenReturn(FALSE_VALUE);
        when(action.getUnless()).thenReturn(FALSE_VALUE);
        
        boolean executable = evaluator.isExecutable(action, context);
        
        assertThat(executable, is(false));
    }
}
