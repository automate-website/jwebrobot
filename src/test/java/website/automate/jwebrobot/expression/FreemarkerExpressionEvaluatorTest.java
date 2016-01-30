package website.automate.jwebrobot.expression;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.util.Collections;

import org.junit.Test;

public class FreemarkerExpressionEvaluatorTest {

    private FreemarkerExpressionEvaluator evaluator = new FreemarkerExpressionEvaluator();
    
    @Test
    public void simpleExpressionIsEvaluated(){
        Object actualResult = evaluator.evaluate("i'm feeling ${mood}!", Collections.singletonMap("mood", (Object)"great"));
        
        assertThat(actualResult.toString(), is("i'm feeling great!"));
    }
}
