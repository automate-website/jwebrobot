package website.automate.jwebrobot.executor.action;

import org.openqa.selenium.WebDriver;

import com.google.inject.Inject;

import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.expression.ExpressionEvaluator;
import website.automate.jwebrobot.model.Action;
import website.automate.jwebrobot.model.ActionType;

public class OpenActionExecutor extends ConditionalActionExecutor {

    @Inject
    public OpenActionExecutor(ExpressionEvaluator expressionEvaluator) {
        super(expressionEvaluator);
    }

    @Override
    public ActionType getActionType() {
        return ActionType.OPEN;
    }

    @Override
    public void perform(final Action action, ScenarioExecutionContext context) {
        WebDriver driver = context.getDriver();
        driver.get(action.getUrl());
    }

}
