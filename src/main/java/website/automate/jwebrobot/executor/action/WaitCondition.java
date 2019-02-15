package website.automate.jwebrobot.executor.action;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.executor.ActionExecutorUtils;
import website.automate.jwebrobot.executor.ActionResult;
import website.automate.jwebrobot.mapper.BooleanMapper;
import website.automate.waml.io.model.main.action.FilterAction;
import website.automate.waml.io.model.main.criteria.FilterCriteria;

import java.util.function.Function;

public class WaitCondition implements Function<WebDriver, WebElement> {

    private FilterAction<? extends FilterCriteria> action;

    private ScenarioExecutionContext context;

    private ActionResult result;

    private ActionExecutorUtils utils;

    public static final WebElement EMPTY = new RemoteWebElement();

    public WaitCondition(
        FilterAction<? extends FilterCriteria> action,
        ScenarioExecutionContext context,
        ActionResult result,
        ActionExecutorUtils utils
    ) {
        this.action = action;
        this.context = context;
        this.result = result;
        this.utils = utils;
    }

    @Override
    public WebElement apply(WebDriver webDriver) {
        final boolean invert = getInvert();
        final WebElement element = getElement();

        if(isNotFound(element)){
            return getEmptyElementIfInvertOrNull(invert);
        }

        return getNullIfInvertOrElement(invert, element);
    }

    private boolean getInvert(){
        return BooleanMapper.isTruthy(action.getFilter().getInvert());
    }

    private WebElement getElement(){
        return utils.getElementsFilter().filter(context, action);
    }

    private WebElement getNullIfInvertOrElement(boolean invert, WebElement element){
        result.setValue(element);

        if(invert){
            return null;
        }
        return element;
    }

    private boolean isNotFound(WebElement element){
        return element == null;
    }

    private WebElement getEmptyElementIfInvertOrNull(boolean invert){
        return invert ? EMPTY : null;
    }
}
