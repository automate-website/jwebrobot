package website.automate.jwebrobot.executor;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.executor.filter.ElementFilter;
import website.automate.jwebrobot.executor.filter.ElementsFilter;
import website.automate.waml.io.model.action.ElementStoreAction;

import java.util.Map;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Service
public class WebElementProvider {

    private TimeoutResolver timeoutResolver;

    private ElementsFilter elementsFilter;

    @Autowired
    public WebElementProvider(TimeoutResolver timeoutResolver,
                              ElementsFilter elementsFilter){
        this.timeoutResolver = timeoutResolver;
        this.elementsFilter = elementsFilter;
    }

    public WebElement get(ElementStoreAction action,
                             ScenarioExecutionContext context,
                             WebDriver driver){
        return (
            new WebDriverWait(
                driver,
                timeoutResolver.resolve(action, context)
            )
        ).until(condition -> elementsFilter.filter(context, action));
    }
}
