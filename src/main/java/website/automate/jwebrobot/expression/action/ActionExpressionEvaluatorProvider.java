package website.automate.jwebrobot.expression.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import website.automate.waml.io.model.main.action.Action;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
public class ActionExpressionEvaluatorProvider {

    private Map<Class<? extends Action>, ActionExpressionEvaluator<?>> actionExpressionEvaluatorMap = new HashMap<>();
    
    @Autowired
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
