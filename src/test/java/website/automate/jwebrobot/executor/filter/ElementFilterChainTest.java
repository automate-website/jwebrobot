package website.automate.jwebrobot.executor.filter;

import static java.util.Arrays.asList;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.model.Action;
import website.automate.jwebrobot.model.CriteriaType;
import website.automate.jwebrobot.model.CriteriaValue;

@RunWith(MockitoJUnitRunner.class)
public class ElementFilterChainTest {

    @Mock private ElementFilter selectorElementFilter;
    @Mock private ElementFilter textElementFilter;
    @Mock private ElementFilterProvider provider;
    @Mock private ScenarioExecutionContext context;
    @Mock private Action action;
    @Mock private WebDriver driver;
    @Mock private WebElement body;
    @Mock private WebElement parent;
    @Mock private WebElement target;
    
    private ElementFilterChain chain;
    
    private Map<String, CriteriaValue> criteriaValueMap = new HashMap<>();
    private Map<String, CriteriaValue> parentCriteriaValueMap = new HashMap<>();
    
    private CriteriaValue selectorValue = new CriteriaValue("h2");
    private CriteriaValue textValue = new CriteriaValue("interesting article");
    
    @Test
    public void elementDescribedByActionAndParentCriteriaIsFound(){
        chain = new ElementFilterChain(provider);
        criteriaValueMap.put(CriteriaType.SELECTOR.getName(), selectorValue);
        criteriaValueMap.put(CriteriaType.PARENT.getName(), new CriteriaValue(parentCriteriaValueMap));
        parentCriteriaValueMap.put(CriteriaType.TEXT.getName(), textValue);
        
        when(provider.getInstance(CriteriaType.SELECTOR)).thenReturn(selectorElementFilter);
        when(provider.getInstance(CriteriaType.TEXT)).thenReturn(textElementFilter);
        when(context.getDriver()).thenReturn(driver);
        when(driver.findElement(By.tagName("html"))).thenReturn(body);
        when(action.getCriteriaValueMap()).thenReturn(criteriaValueMap);
        when(selectorElementFilter.filter(selectorValue, asList(parent))).thenReturn(asList(target));
        when(textElementFilter.filter(textValue, asList(body))).thenReturn(asList(parent));
        
        List<WebElement> actualWebElements = chain.filter(context, action);
        assertThat(actualWebElements.size(), is(1));
        assertThat(actualWebElements.get(0), is(target));
    }
}
