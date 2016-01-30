package website.automate.jwebrobot.expression;

import java.util.Map;

import website.automate.jwebrobot.exceptions.ExpressionEvaluationException;

public interface ExpressionEvaluator {

    Object evaluate(String expression, Map<String, Object> memory) throws ExpressionEvaluationException;
}
