package website.automate.jwebrobot.executor.action;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Service;
import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.executor.ActionExecutorUtils;
import website.automate.jwebrobot.executor.ActionResult;
import website.automate.jwebrobot.mapper.BooleanMapper;
import website.automate.waml.io.model.main.action.ExecuteAction;
import website.automate.waml.io.model.main.criteria.ExecuteCriteria;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class ExecuteActionExecutor extends BaseActionExecutor<ExecuteAction> {

    @Override
    public void execute(
        ExecuteAction action,
        ScenarioExecutionContext context,
        ActionResult result,
        ActionExecutorUtils utils
    ) {
        final WebDriver driver = context.getDriver();
        driver.manage().timeouts().setScriptTimeout(utils.getTimeoutResolver().resolve(action, context), TimeUnit.SECONDS);

        final ExecuteCriteria executeCriteria = action.getExecute();
        final String script = executeCriteria.getScript();
        final boolean async = BooleanMapper.isTruthy(executeCriteria.getAsync());

        JavascriptExecutor js = (JavascriptExecutor)driver;

        final Map<String, Object> totalMemory = context.getTotalMemory();
        final Object resultObject;
        if (async) {
            resultObject = js.executeAsyncScript(script, totalMemory);
        } else {
            resultObject = js.executeScript(script, totalMemory);
        }

        result.setValue(resultObject);
    }

    @Override
    public Class<ExecuteAction> getSupportedType() {
        return ExecuteAction.class;
    }
}
