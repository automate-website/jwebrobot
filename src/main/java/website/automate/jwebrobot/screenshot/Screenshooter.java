package website.automate.jwebrobot.screenshot;

import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Service;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;
import ru.yandex.qatools.ashot.shooting.ShootingStrategy;
import website.automate.jwebrobot.context.GlobalExecutionContext;
import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.exceptions.NonReadableFileException;
import website.automate.jwebrobot.executor.ExecutorOptions.ScreenshotType;
import website.automate.jwebrobot.utils.ViewPortShootingStrategy;
import website.automate.waml.io.model.main.action.Action;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@Service
public class Screenshooter {
    
    public void takeScreenshot(ScenarioExecutionContext scenarioContext, Action action){
        WebDriver driver = scenarioContext.getDriver();

        if(driver instanceof TakesScreenshot){
        	ScenarioExecutionContext scenarioRootContext = scenarioContext.getRoot();
            BufferedImage screenshot = new AShot()
	            .shootingStrategy(getShootingStrategy(scenarioRootContext))
	            .takeScreenshot(driver).getImage();
            String format = getScreenshotFormat(scenarioRootContext);
            String targetFileName = getFileName(scenarioRootContext, action, format);
            try {
            	ImageIO.write(screenshot, format, new File(targetFileName));
            } catch (IOException e) {
                throw new NonReadableFileException(targetFileName, e);
            }
        }
    }
    
    private String getScreenshotFormat(ScenarioExecutionContext context){
    	return context.getGlobalContext().getOptions().getScreenshotFormat();
    }
    
    private ShootingStrategy getShootingStrategy(ScenarioExecutionContext context){
    	ScreenshotType type = context.getGlobalContext().getOptions().getScreenshotType();
    	if(type == ScreenshotType.FULLSCREEN){
    		return ShootingStrategies.viewportPasting(100);
    	} else {
    		return new ViewPortShootingStrategy();
    	}
    }
    
    private String getFileName(ScenarioExecutionContext scenarioRootContext,  Action action, String format){
        GlobalExecutionContext globalContext = scenarioRootContext.getGlobalContext();
        String targetPath = globalContext.getOptions().getScreenshotPath();
        
        return targetPath + scenarioRootContext.getScenario().getName() + "_" + scenarioRootContext.getStepCount() + "_" + action.getTypeName() + "." + format;
    }
}
