package website.automate.jwebrobot.executor.filter;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static website.automate.waml.io.model.CriterionValue.of;

import java.util.List;

import org.junit.Test;
import org.openqa.selenium.WebElement;

public class SelectorElementFilterIT extends ElementFilterTestBase {

    private SelectorElementFilter filter = new SelectorElementFilter();
    
    @Test
    public void elementsAreSelectedBySelector(){
        List<WebElement> webElements = filter.filter(of("h1.central-textlogo"), getBody());
        
        assertThat(webElements.size(), is(1));
    }
}
