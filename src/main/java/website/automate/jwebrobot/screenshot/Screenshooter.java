package website.automate.jwebrobot.screenshot;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import website.automate.jwebrobot.context.GlobalExecutionContext;
import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.exceptions.NonReadableFileException;
import website.automate.jwebrobot.model.Action;

public class Screenshooter {
    
    public void takeScreenshot(ScenarioExecutionContext context, Action action){
        WebDriver driver = context.getDriver();

        if(driver instanceof TakesScreenshot){
            TakesScreenshot screenshotCapableDriver = (TakesScreenshot)driver;
            File sourceFile = screenshotCapableDriver.getScreenshotAs(OutputType.FILE);
            String targetFileName = getFileName(context, action);
            try {
                FileUtils.copyFile(sourceFile, new File(getFileName(context, action)));
            } catch (IOException e) {
                throw new NonReadableFileException(targetFileName, e);
            }
        }
    }
    
    private String getFileName(ScenarioExecutionContext context,  Action action){
        GlobalExecutionContext globalContext = context.getGlobalContext();
        String targetPath = globalContext.getOptions().getScreenshotPath();
        
        return targetPath + context.getSessionId() + "_" + context.getRootScenario().getName() + "_" + action.getType().getName() + ".png"; 
    }
}
