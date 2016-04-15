package website.automate.jwebrobot.executor.filter;

import static java.util.Arrays.asList;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.waml.io.model.CriterionType;
import website.automate.waml.io.model.action.FilterAction;
import website.automate.waml.io.model.action.ParentCriteria;

@RunWith(MockitoJUnitRunner.class)
public class ElementFilterChainTest {

    @Mock private ElementFilter selectorElementFilter;
    @Mock private ElementFilter textElementFilter;
    @Mock private ElementFilterProvider provider;
    @Mock private ScenarioExecutionContext context;
    @Mock private FilterAction action;
    @Mock private ParentCriteria parentCriteria;
    @Mock private WebDriver driver;
    @Mock private WebElement body;
    @Mock private WebElement parent;
    @Mock private WebElement target;
    
    private ElementFilterChain chain;
    
    private static final String 
            SELECTOR = "h2",
            PARENT_TEXT = "interesting article";
    
    private String selectorValue = "h2";
    private String textValue = "interesting article";
    
    @Test
    public void elementDescribedByActionAndParentCriteriaIsFound(){
        chain = new ElementFilterChain(provider);
        when(parent.isDisplayed()).thenReturn(Boolean.TRUE);
        when(target.isDisplayed()).thenReturn(Boolean.TRUE);
        when(action.getSelector()).thenReturn(SELECTOR);
        when(action.getParent()).thenReturn(parentCriteria);
        when(parentCriteria.getText()).thenReturn(PARENT_TEXT);
        
        when(provider.getInstance(CriterionType.SELECTOR)).thenReturn(selectorElementFilter);
        when(provider.getInstance(CriterionType.TEXT)).thenReturn(textElementFilter);
        when(context.getDriver()).thenReturn(driver);
        when(driver.findElement(By.tagName("html"))).thenReturn(body);
        when(selectorElementFilter.filter(selectorValue, asList(parent))).thenReturn(asList(target));
        when(textElementFilter.filter(textValue, asList(body))).thenReturn(asList(parent));
        
        List<WebElement> actualWebElements = chain.filter(context, action);
        assertThat(actualWebElements.size(), is(1));
        assertThat(actualWebElements.get(0), is(target));
    }
}
