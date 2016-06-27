package website.automate.jwebrobot.executor.action;

import static java.text.MessageFormat.format;

import java.net.URL;

import org.openqa.selenium.WebDriver;

import com.google.inject.Inject;

import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.expression.ConditionalExpressionEvaluator;
import website.automate.jwebrobot.expression.ExpressionEvaluator;
import website.automate.jwebrobot.listener.ExecutionEventListeners;
import website.automate.waml.io.model.action.OpenAction;

public class OpenActionExecutor extends ConditionalActionExecutor<OpenAction> {

    @Inject
    public OpenActionExecutor(ExpressionEvaluator expressionEvaluator,
            ExecutionEventListeners listener,
            ConditionalExpressionEvaluator conditionalExpressionEvaluator) {
        super(expressionEvaluator, listener,
                conditionalExpressionEvaluator);
    }

    @Override
    public void perform(final OpenAction action, ScenarioExecutionContext context) {
        WebDriver driver = context.getDriver();
        driver.get(safeGetUrl(action.getUrl()));
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
			throw new website.automate.jwebrobot.exceptions.MalformedURLException(format("Failed parsing open action url {0}", malformedUrlStr), e);
		}
    	return url.toString();
    }
}
