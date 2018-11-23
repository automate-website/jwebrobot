package website.automate.jwebrobot.screenshot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import website.automate.jwebrobot.context.GlobalExecutionContext;
import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.executor.ExecutorOptions.TakeScreenshots;
import website.automate.jwebrobot.listener.ExecutionEventListener;
import website.automate.waml.io.model.main.action.Action;

@Service
public class ScreenshotEventListener implements ExecutionEventListener {

    private Screenshooter screenshooter;
    
    @Autowired
    public ScreenshotEventListener(Screenshooter screenshooter) {
        this.screenshooter = screenshooter;
    }

    @Override
    public void afterAction(ScenarioExecutionContext context, Action action) {
        if(context.getGlobalContext().getOptions().getTakeScreenshots() == TakeScreenshots.ON_EVERY_STEP
                && Action.isExplicit(action.getClass())){
            screenshooter.takeScreenshot(context, action);
        }
    }

    @Override
    public void errorAction(ScenarioExecutionContext context, Action action,
            Exception exception) {
        if(context.getGlobalContext().getOptions().getTakeScreenshots() != TakeScreenshots.NEVER
                && Action.isExplicit(action.getClass())){
            screenshooter.takeScreenshot(context, action);
        }
    }
    
    @Override
    public void beforeExecution(GlobalExecutionContext context) {
    }

    @Override
    public void afterExecution(GlobalExecutionContext context) {
    }

    @Override
    public void errorExecution(GlobalExecutionContext context,
            Exception exception) {
    }

    @Override
    public void beforeScenario(ScenarioExecutionContext context) {
    }

    @Override
    public void afterScenario(ScenarioExecutionContext context) {
    }

    @Override
    public void errorScenario(ScenarioExecutionContext context,
            Exception exception) {
    }

    @Override
    public void beforeAction(ScenarioExecutionContext context, Action action) {
    }
}
