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
import org.openqa.selenium.WebDriver.TargetLocator;
import org.openqa.selenium.WebElement;

import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.waml.io.model.action.FilterAction;
import website.automate.waml.io.model.criteria.FilterCriteria;

@RunWith(MockitoJUnitRunner.class)
public class ElementFilterChainTest {

    @Mock private SelectorElementFilter selectorElementFilter;
    @Mock private TextElementFilter textElementFilter;
    @Mock private ValueElementFilter valueElementFilter;
    @Mock private ScenarioExecutionContext context;
    @Mock private FilterAction<FilterCriteria> action;
    @Mock private FilterCriteria criteria;
    @Mock private WebDriver driver;
    @Mock private WebElement body;
    @Mock private WebElement parent;
    @Mock private WebElement target;
    @Mock private TargetLocator locator;
    
    private ElementFilterChain chain;
    
    private static final String 
            SELECTOR = "h2",
            PARENT = "parent-element";
    
    private String selectorValue = "h2";
    private String textValue = "interesting article";
    
    @Test
    public void elementDescribedByActionAndParentCriteriaIsFound(){
        chain = new ElementFilterChain(selectorElementFilter, textElementFilter, valueElementFilter);
        when(parent.isDisplayed()).thenReturn(Boolean.TRUE);
        when(target.isDisplayed()).thenReturn(Boolean.TRUE);
        when(action.getFilter()).thenReturn(criteria);
        when(criteria.getSelector()).thenReturn(SELECTOR);
        when(criteria.getParent()).thenReturn(PARENT);

        when(context.getDriver()).thenReturn(driver);
        when(driver.switchTo()).thenReturn(locator);
        when(locator.defaultContent()).thenReturn(driver);
        when(parent.getTagName()).thenReturn("div");
        when(target.getTagName()).thenReturn("a");
        when(driver.findElement(By.tagName("html"))).thenReturn(body);
        when(selectorElementFilter.filter(selectorValue, asList(parent))).thenReturn(asList(target));
        when(textElementFilter.filter(textValue, asList(body))).thenReturn(asList(parent));
        
        List<WebElement> actualWebElements = chain.filter(context, action);
        assertThat(actualWebElements.size(), is(1));
        assertThat(actualWebElements.get(0), is(target));
    }
}
