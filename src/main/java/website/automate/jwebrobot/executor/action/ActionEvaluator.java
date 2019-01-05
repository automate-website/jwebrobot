package website.automate.jwebrobot.executor.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.expression.action.ActionExpressionEvaluator;
import website.automate.jwebrobot.expression.action.ActionExpressionEvaluatorProvider;
import website.automate.waml.io.deserializer.UnknownActionException;
import website.automate.waml.io.model.main.action.Action;

@Service
public class ActionEvaluator {

    private ActionExpressionEvaluatorProvider actionExpressionEvaluatorProvider;
    
    @Autowired
    public ActionEvaluator(ActionExpressionEvaluatorProvider actionExpressionEvaluatorProvider){
        this.actionExpressionEvaluatorProvider = actionExpressionEvaluatorProvider;
    }
    
    public <T extends Action> T evaluate(T action, ScenarioExecutionContext context){
        ActionExpressionEvaluator<Action> actionExpressionEvaluator = actionExpressionEvaluatorProvider.getInstance(action.getClass());
        if (actionExpressionEvaluator == null) {
            throw new UnknownActionException(action.toString());
        }
        actionExpressionEvaluator.evaluateTemplateAsString(action, context);
        return (T)action;
    }
}
