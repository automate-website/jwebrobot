package website.automate.jwebrobot.utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import ru.yandex.qatools.ashot.coordinates.Coords;
import ru.yandex.qatools.ashot.shooting.SimpleShootingStrategy;
import ru.yandex.qatools.ashot.util.InnerScript;

import java.awt.image.BufferedImage;
import java.util.Set;

import static ru.yandex.qatools.ashot.util.InnerScript.VIEWPORT_HEIGHT_JS;
import static ru.yandex.qatools.ashot.util.InnerScript.VIEWPORT_WIDTH_JS;

public class ViewPortShootingStrategy extends SimpleShootingStrategy {

	private static final long serialVersionUID = 3353639241445553638L;
	
	public static final String 
		WINDOW_SCROLL_Y_JS = "return window.scrollY;",
		WINDOW_SCROLL_X_JS = "return window.scrollX;";
	
	@Override
	public BufferedImage getScreenshot(WebDriver driver) {
		Coords viewPortCoords = getShootingCords(driver);
		BufferedImage screenshot = super.getScreenshot(driver);
		
		int height = Math.min(screenshot.getHeight(), viewPortCoords.height);
		int width = Math.min(screenshot.getWidth(), viewPortCoords.width);
		
		int maxCutWidth = Math.min(screenshot.getWidth(), viewPortCoords.x + width);
		int maxCutHeight = Math.min(screenshot.getHeight(), viewPortCoords.y + height);
		
		int x = Math.min(maxCutWidth - width, viewPortCoords.x);
		int y = Math.min(maxCutHeight - height, viewPortCoords.y);

		return screenshot.getSubimage(x, 
				y, 
				width, 
				height);
	}

	@Override
	public BufferedImage getScreenshot(WebDriver driver, Set<Coords> coords) {
		return getScreenshot(driver);
	}

	@Override
	public Set<Coords> prepareCoords(Set<Coords> coordsSet) {
		return coordsSet;
	}
	
	private Coords getShootingCords(WebDriver driver){
		return new Coords(getScroolX(driver), 
				getScroolY(driver), 
				getWindowWidth(driver), 
				getWindowHeight(driver));
	}
	
	private int getScroolY(WebDriver driver){
		return ((Number) execute(WINDOW_SCROLL_Y_JS, driver)).intValue();
	}
	
	private int getScroolX(WebDriver driver){
		return ((Number) execute(WINDOW_SCROLL_X_JS, driver)).intValue();
	}
	
	private int getWindowWidth(WebDriver driver) {
        return ((Number) InnerScript.execute(VIEWPORT_WIDTH_JS, driver)).intValue();
    }

	private int getWindowHeight(WebDriver driver) {
        return ((Number) InnerScript.execute(VIEWPORT_HEIGHT_JS, driver)).intValue();
    }
	
	@SuppressWarnings("unchecked")
	private static <T> T execute(String script, WebDriver driver, Object... args) {
        try {
            return (T) ((JavascriptExecutor) driver).executeScript(script, args);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
