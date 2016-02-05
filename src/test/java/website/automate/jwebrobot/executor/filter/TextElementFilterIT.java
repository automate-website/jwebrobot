package website.automate.jwebrobot.executor.filter;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;
import org.openqa.selenium.WebElement;

import website.automate.jwebrobot.model.CriteriaValue;

public class TextElementFilterIT extends ElementFilterTestBase {

    private TextElementFilter filter = new TextElementFilter();
    
    @Test
    public void elementsAreSelectedByText(){
        List<WebElement> webElements = filter.filter(new CriteriaValue("The Free Encyclopedia"), getBody());
        
        assertThat(webElements.size(), is(1));
    }
}
