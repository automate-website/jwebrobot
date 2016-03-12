package website.automate.jwebrobot.executor.filter;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static website.automate.waml.io.model.CriterionValue.of;

import java.util.List;

import org.junit.Test;
import org.openqa.selenium.WebElement;

public class TextElementFilterIT extends ElementFilterTestBase {

    private TextElementFilter filter = new TextElementFilter();
    
    @Test
    public void elementsAreSelectedByText(){
        List<WebElement> webElements = filter.filter(of("The Free Encyclopedia"), getBody());
        
        assertThat(webElements.size(), is(1));
    }
}
