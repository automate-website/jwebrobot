package website.automate.jwebrobot.expression;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.waml.io.model.main.action.ConditionalAction;

@Service
public class ConditionalExpressionEvaluator {

    private ExpressionEvaluator expressionEvaluator;

    @Autowired
    public ConditionalExpressionEvaluator(ExpressionEvaluator expressionEvaluator){
        this.expressionEvaluator = expressionEvaluator;
    }

    public boolean isExecutable(ConditionalAction action, ScenarioExecutionContext context){
        return isExecutable(action.getWhen(), action.getUnless(), context);
    }

    private boolean isExecutable(String when, String unless, ScenarioExecutionContext context){
        boolean executeIf = evaluate(when, context, true);
        boolean executeUnless = evaluate(unless, context, false);

        return executeIf && !executeUnless;
    }

    private boolean evaluate(String value, ScenarioExecutionContext context, boolean defaultValue){
        if(value == null){
            return defaultValue;
        }
        return expressionEvaluator.evaluate(value, context.getTotalMemory(), Boolean.class);
    }
}
