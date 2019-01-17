package website.automate.jwebrobot.expression;

import io.codearte.jfairy.Fairy;
import org.junit.Test;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import java.util.HashMap;

import static java.util.Collections.singletonMap;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

public class SpelExpressionEvaluatorTest {

    private SpelExpressionEvaluator evaluator = new SpelExpressionEvaluator(new SpelExpressionParser(), new TemplateParserContext(), Fairy.create());
    
    @Test
    public void simpleExpressionIsEvaluated(){
        Object actualResult = evaluator.evaluate("i'm feeling ${mood}!", singletonMap("mood", (Object)"great"),  String.class);
        
        assertThat(actualResult.toString(), is("i'm feeling great!"));
    }
    
    @Test
    public void mockDataIsGenerated(){
        Object actualResult = evaluator.evaluate("${ _.mock.company().name() }", new HashMap<String, Object>(),  String.class);
        
        assertFalse(actualResult.toString().isEmpty());
    }
}
