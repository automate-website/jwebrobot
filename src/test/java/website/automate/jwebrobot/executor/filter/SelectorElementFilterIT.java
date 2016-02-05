package website.automate.jwebrobot.executor.filter;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;
import org.openqa.selenium.WebElement;

import website.automate.jwebrobot.model.CriteriaValue;

public class SelectorElementFilterIT extends ElementFilterTestBase {

    private SelectorElementFilter filter = new SelectorElementFilter();
    
    @Test
    public void elementsAreSelectedBySelector(){
        List<WebElement> webElements = filter.filter(new CriteriaValue("h1.central-textlogo"), getBody());
        
        assertThat(webElements.size(), is(1));
    }
}
