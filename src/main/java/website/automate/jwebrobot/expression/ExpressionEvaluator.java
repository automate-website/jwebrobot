package website.automate.jwebrobot.expression;

import website.automate.jwebrobot.exceptions.ExpressionEvaluationException;

import java.util.Map;

public interface ExpressionEvaluator {

    String evaluate(String expression, Map<String, Object> memory) throws ExpressionEvaluationException;
}
