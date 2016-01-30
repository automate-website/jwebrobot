package website.automate.jwebrobot.listener;

import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.model.Action;

public class MockExecutionEventListener implements ExecutionEventListener {

    @Override
    public void beforeScenario(ScenarioExecutionContext context) {
    }

    @Override
    public void afterScenario(ScenarioExecutionContext context) {
    }

    @Override
    public void errorScenario(ScenarioExecutionContext context) {
    }

    @Override
    public void beforeAction(ScenarioExecutionContext context, Action action) {
    }

    @Override
    public void afterAction(ScenarioExecutionContext context, Action action) {
    }

    @Override
    public void errorAction(ScenarioExecutionContext context, Action action) {
    }

}
