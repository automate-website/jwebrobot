package website.automate.jwebrobot.executor.filter;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static website.automate.waml.io.model.CriterionValue.of;

import java.util.List;

import org.junit.Test;
import org.openqa.selenium.WebElement;

public class ValueElementFilterIT extends ElementFilterTestBase {

    private ValueElementFilter filter = new ValueElementFilter();
    
    @Test
    public void elementsAreSelectedByValueAttribute(){
        List<WebElement> webElements = filter.filter(of("en"), getBody());
        
        assertThat(webElements.size() > 0, is(true));
    }
}
