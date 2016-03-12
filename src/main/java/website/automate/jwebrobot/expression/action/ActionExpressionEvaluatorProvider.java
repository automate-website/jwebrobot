package website.automate.jwebrobot.expression.action;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.google.inject.Inject;

import website.automate.waml.io.model.action.Action;

public class ActionExpressionEvaluatorProvider {

    private Map<Class<? extends Action>, ActionExpressionEvaluator<?>> actionExpressionEvaluatorMap = new HashMap<>();
    
    @Inject
    public ActionExpressionEvaluatorProvider(Set<ActionExpressionEvaluator<?>> actionExpressionEvaluators){
        for(ActionExpressionEvaluator<?> actionExpressionEvaluator : actionExpressionEvaluators){
            actionExpressionEvaluatorMap.put(actionExpressionEvaluator.getSupportedType(), actionExpressionEvaluator);
        }
    }
    
    @SuppressWarnings("unchecked")
    public ActionExpressionEvaluator<Action> getInstance(Class<? extends Action> actionClazz){
        return (ActionExpressionEvaluator<Action>) actionExpressionEvaluatorMap.get(actionClazz);
    }
}
