package website.automate.jwebrobot.expression;

import java.util.Map;

public interface ExpressionEvaluator {

    Object evaluate(String expression, Map<String, Object> memory);
}
