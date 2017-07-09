package website.automate.jwebrobot.executor.action;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

import java.util.Map;
import java.util.Map.Entry;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.inject.Inject;

import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.exceptions.ExceptionTranslator;
import website.automate.jwebrobot.executor.filter.ElementFilterChain;
import website.automate.jwebrobot.expression.ConditionalExpressionEvaluator;
import website.automate.jwebrobot.expression.ExpressionEvaluator;
import website.automate.jwebrobot.listener.ExecutionEventListeners;
import website.automate.waml.io.model.action.StoreAction;

public class StoreActionExecutor extends ElementStoreActionExecutor<StoreAction> {

    private static final RemoteWebElement EMPTY_ELEMENT = new RemoteWebElement();
  
    @Inject
    public StoreActionExecutor(ExpressionEvaluator expressionEvaluator,
            ExecutionEventListeners listener, ElementFilterChain elementFilterChain,
            ConditionalExpressionEvaluator conditionalExpressionEvaluator,
            ExceptionTranslator exceptionTranslator) {
        super(expressionEvaluator, listener,
                elementFilterChain,
                conditionalExpressionEvaluator,
                exceptionTranslator);
    }

    @Override
    public void perform(final StoreAction action, final ScenarioExecutionContext context) {
        storeElementIfRequestedAndFound(action, context);
        
        Map<String, Object> memory = context.getMemory();
        
        Map<String, String> criteriaValueMap = action.getFacts();
        
        for(Entry<String, String> criteriaValueEntry : criteriaValueMap.entrySet()){
            memory.put(criteriaValueEntry.getKey(), criteriaValueEntry.getValue());
        }
    }
    
    private void storeElementIfRequestedAndFound(final StoreAction action, final ScenarioExecutionContext context){
      if(isNotBlank(action.getStore())){
        storeElementIfFound(action, context);
      }
    }
    
    private void storeElementIfFound(final StoreAction action, final ScenarioExecutionContext context){
      WebElement webElement = findElement(action, context);

      if(webElement != EMPTY_ELEMENT){
        String memoryKey = action.getStore();
        context.getMemory().put(memoryKey, webElement);
      }
    }
    
    private WebElement findElement(final StoreAction action, final ScenarioExecutionContext context){
      WebDriver driver = context.getDriver();
      long timeoutInSeconds = getActionTimeout(action, context);
      final long timeoutInMillis = timeoutInSeconds * 1000;
      final long startTimeInMillis = System.currentTimeMillis();
      
      return (new WebDriverWait(driver, timeoutInSeconds).until(new ExpectedCondition<WebElement>() {
        public WebElement apply(WebDriver d) {
          WebElement filteredWebElement = filter(context, action);

          if(isTimeout(startTimeInMillis, timeoutInMillis) && filteredWebElement == null){
            return EMPTY_ELEMENT;
          }

          return filteredWebElement;
        }
      }));
    }
    
    private boolean isTimeout(long startTimeInMillis, long timeoutInMillis){
      long currentTimeInMillis = System.currentTimeMillis();
      return (startTimeInMillis + timeoutInMillis) < currentTimeInMillis;
    }

    @Override
    public Class<StoreAction> getSupportedType() {
        return StoreAction.class;
    }

}
