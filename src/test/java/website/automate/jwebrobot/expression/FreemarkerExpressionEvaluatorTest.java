package website.automate.jwebrobot.expression;

import org.junit.Test;

import java.util.Collections;
import java.util.HashMap;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

public class FreemarkerExpressionEvaluatorTest {

    private FreemarkerExpressionEvaluator evaluator = new FreemarkerExpressionEvaluator();
    
    @Test
    public void simpleExpressionIsEvaluated(){
        Object actualResult = evaluator.evaluate("i'm feeling ${mood}!", Collections.singletonMap("mood", (Object)"great"));
        
        assertThat(actualResult.toString(), is("i'm feeling great!"));
    }
    
    @Test
    public void mockDataIsGenerated(){
        Object actualResult = evaluator.evaluate("${ _.mock.company().name() }", new HashMap<String, Object>());
        
        assertFalse(actualResult.toString().isEmpty());
    }
}
