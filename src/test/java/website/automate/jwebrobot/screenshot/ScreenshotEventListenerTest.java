package website.automate.jwebrobot.screenshot;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import website.automate.jwebrobot.context.GlobalExecutionContext;
import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.executor.ExecutorOptions;
import website.automate.jwebrobot.executor.ExecutorOptions.TakeScreenshots;
import website.automate.waml.io.model.main.action.ClickAction;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ScreenshotEventListenerTest {

    @Mock private Screenshooter screenshooter;
    @Mock private ScenarioExecutionContext context;
    @Mock private GlobalExecutionContext globalContext;
    @Mock private ExecutorOptions options;
    @Mock private Exception exception;
    
    private ClickAction action = new ClickAction();
    private ScreenshotEventListener listener;
    
    @Before
    public void init(){
        when(context.getGlobalContext()).thenReturn(globalContext);
        when(globalContext.getOptions()).thenReturn(options);
        
        listener = new ScreenshotEventListener(screenshooter);
    }
    
    @Test
    public void screenshotIsTakenOnFailureIfConfiguredToTakeScreenshotsOnFailure(){
        when(options.getTakeScreenshots()).thenReturn(TakeScreenshots.ON_FAILURE);
        
        listener.errorAction(context, action, exception);
        
        verify(screenshooter).takeScreenshot(context, action);
    }
    
    @Test
    public void screenshotIsTakenOnFailureIfConfiguredToTakeScreenshotsOnEveryStep(){
        when(options.getTakeScreenshots()).thenReturn(TakeScreenshots.ON_FAILURE);
        
        listener.errorAction(context, action, exception);
        
        verify(screenshooter).takeScreenshot(context, action);
    }
    
    @Test
    public void screenshotIsNotTakenOnFailureIfConfiguredNeverToTakeScreenshots(){
        when(options.getTakeScreenshots()).thenReturn(TakeScreenshots.NEVER);
        
        listener.errorAction(context, action, exception);
        
        verify(screenshooter, never()).takeScreenshot(context, action);
    }
    
    @Test
    public void screenshotIsTakenOnAfterActionIfConfiguredToTakeScreenshotsOnEveryStep(){
        when(options.getTakeScreenshots()).thenReturn(TakeScreenshots.ON_EVERY_STEP);
        
        listener.errorAction(context, action, exception);
        
        verify(screenshooter).takeScreenshot(context, action);
    }
}
