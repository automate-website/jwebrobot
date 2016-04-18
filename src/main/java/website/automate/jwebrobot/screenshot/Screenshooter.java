package website.automate.jwebrobot.screenshot;

import static website.automate.waml.io.model.ActionType.findByClazz;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import website.automate.jwebrobot.context.GlobalExecutionContext;
import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.exceptions.NonReadableFileException;
import website.automate.waml.io.model.action.Action;

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
        ScenarioExecutionContext rootContext = context.getRoot();
        
        return targetPath + rootContext.getScenario().getName() + "_" + rootContext.getStepCount() + "_" + findByClazz(action.getClass()).getName() + ".png"; 
    }
}
