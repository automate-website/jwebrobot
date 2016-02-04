package website.automate.jwebrobot.screenshot;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import website.automate.jwebrobot.context.GlobalExecutionContext;
import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.executor.ExecutorOptions;
import website.automate.jwebrobot.executor.ExecutorOptions.TakeScreenshots;
import website.automate.jwebrobot.model.Action;

@RunWith(MockitoJUnitRunner.class)
public class ScreenshotEventListenerTest {

    @Mock private Screenshooter screenshooter;
    @Mock private ScenarioExecutionContext context;
    @Mock private GlobalExecutionContext globalContext;
    @Mock private ExecutorOptions options;
    @Mock private Action action;
    @Mock private Exception exception;
    
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
