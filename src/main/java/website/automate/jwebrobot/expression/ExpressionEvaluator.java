package website.automate.jwebrobot.expression;

import website.automate.jwebrobot.exceptions.ExpressionEvaluationException;

import java.util.Map;

public interface ExpressionEvaluator {

    <T> T evaluate(String expression, Map<String, Object> memory,
                   Class<T> resultClazz, boolean isTemplate) throws ExpressionEvaluationException;
}
