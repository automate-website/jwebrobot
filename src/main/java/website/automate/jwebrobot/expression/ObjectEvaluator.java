package website.automate.jwebrobot.expression;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

import static java.text.MessageFormat.format;

@Service
public class ObjectEvaluator {

    private SpelExpressionEvaluator expressionEvaluator;

    @Autowired
    public ObjectEvaluator(SpelExpressionEvaluator expressionEvaluator){
       this.expressionEvaluator = expressionEvaluator;
    }

    @SuppressWarnings("unchecked")
    public <T> T evaluate(Object value, Map<String, Object> memory,
                          Class<T> expectedClass){
        if(value == null){
            return null;
        }
        Object result;
        if(value instanceof String){
            result = expressionEvaluator.evaluate((String)value, memory, expectedClass);
        } else {
            result = value;
        }
        Class<?> actualClass = result.getClass();
        if(!expectedClass.isAssignableFrom(actualClass)){
            throw new RuntimeException(
                format("Given object type {0} does not match expected type {1}.",
                    actualClass,
                    expectedClass)
            );
        }
        return (T)result;
    }
}
