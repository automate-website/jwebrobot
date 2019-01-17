package website.automate.jwebrobot.expression;

import io.codearte.jfairy.Fairy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.expression.MapAccessor;
import org.springframework.expression.EvaluationException;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.ParseException;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Service;
import website.automate.jwebrobot.exceptions.ExpressionEvaluationException;

import java.util.HashMap;
import java.util.Map;

import static java.util.Collections.singletonMap;

@Service
public class SpelExpressionEvaluator implements ExpressionEvaluator {

    private ExpressionParser expressionParser;

    private TemplateParserContext parserContext;

    private Fairy mock;

    @Autowired
    public SpelExpressionEvaluator(ExpressionParser expressionParser,
                                   TemplateParserContext parserContext,
                                   Fairy mock){
        this.expressionParser = expressionParser;
        this.parserContext = parserContext;
        this.mock = mock;
    }
    
    @Override
    public <T> T evaluate(String expressionStr, Map<String, Object> memory,
                          Class<T> resultClazz) {
        Map<String, Object> context = new HashMap<>();
        context.putAll(memory);
        context.put("_", singletonMap("mock", mock));
        
        try {
            Expression expression = expressionParser.parseExpression(expressionStr, parserContext);

            StandardEvaluationContext evaluationContext = new StandardEvaluationContext(context);
            evaluationContext.addPropertyAccessor(new MapAccessor());

            return expression.getValue(evaluationContext, resultClazz);
        } catch (EvaluationException | ParseException e) {
            throw new ExpressionEvaluationException(expressionStr, e);
        }
    }
}
