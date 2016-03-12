package website.automate.jwebrobot.executor.action;

import com.google.inject.Inject;

import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.expression.action.ActionExpressionEvaluator;
import website.automate.jwebrobot.expression.action.ActionExpressionEvaluatorProvider;
import website.automate.waml.io.model.action.Action;

public class ActionPreprocessor {

    private ActionExpressionEvaluatorProvider actionExpressionEvaluatorProvider;
    
    @Inject
    public ActionPreprocessor(ActionExpressionEvaluatorProvider actionExpressionEvaluatorProvider){
        this.actionExpressionEvaluatorProvider = actionExpressionEvaluatorProvider;
    }
    
    public Action preprocess(Action action, ScenarioExecutionContext context){
        ActionExpressionEvaluator<Action> actionExpressionEvaluator = actionExpressionEvaluatorProvider.getInstance(action.getClass());
        actionExpressionEvaluator.evaluate(action, context);
        return action;
    }
}
