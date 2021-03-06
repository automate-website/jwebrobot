package website.automate.jwebrobot.executor.action;

import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Service;
import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.executor.ActionExecutorUtils;
import website.automate.jwebrobot.executor.ActionResult;
import website.automate.waml.io.model.main.action.OpenAction;

import java.net.URL;

@Service
public class OpenActionExecutor extends BaseActionExecutor<OpenAction> {

    @Override
    public void execute(OpenAction action,
                        ScenarioExecutionContext context,
                        ActionResult result,
                        ActionExecutorUtils utils) {
        final WebDriver driver = context.getDriver();
        driver.get(safeGetUrl(action.getOpen().getUrl()));
    }

    @Override
    public Class<OpenAction> getSupportedType() {
        return OpenAction.class;
    }

    private String safeGetUrl(String malformedUrlStr){
    	String urlStr = malformedUrlStr;
    	if (!urlStr.toLowerCase().matches("^\\w+://.*")) {
    		urlStr = "http://" + urlStr;
        }
    	URL url;
		try {
			url = new URL(urlStr);
		} catch (java.net.MalformedURLException e) {
			throw new website.automate.jwebrobot.exceptions.MalformedURLException(malformedUrlStr, e);
		}
    	return url.toString();
    }
}
