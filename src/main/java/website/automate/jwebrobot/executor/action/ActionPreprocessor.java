package website.automate.jwebrobot.executor.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.expression.action.ActionExpressionEvaluator;
import website.automate.jwebrobot.expression.action.ActionExpressionEvaluatorProvider;
import website.automate.waml.io.model.action.Action;

@Service
public class ActionPreprocessor {

    private ActionExpressionEvaluatorProvider actionExpressionEvaluatorProvider;
    
    @Autowired
    public ActionPreprocessor(ActionExpressionEvaluatorProvider actionExpressionEvaluatorProvider){
        this.actionExpressionEvaluatorProvider = actionExpressionEvaluatorProvider;
    }
    
    public Action preprocess(Action action, ScenarioExecutionContext context){
        ActionExpressionEvaluator<Action> actionExpressionEvaluator = actionExpressionEvaluatorProvider.getInstance(action.getClass());
        actionExpressionEvaluator.evaluate(action, context);
        return action;
    }
}
