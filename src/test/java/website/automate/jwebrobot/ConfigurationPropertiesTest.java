package website.automate.jwebrobot;

import static java.util.Arrays.asList;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class ConfigurationPropertiesTest {

    private ConfigurationProperties properties = new ConfigurationProperties();
    
    @Test
    public void emptyContextIsReturned(){
        assertThat(properties.getContext(), is((Map<String, Object>)new HashMap<String, Object>()));
    }
    
    @Test
    public void parsedContextIsReturned(){
        properties.setContextEntries(asList("x=a", "y=b"));
        
        Map<String, Object> context = properties.getContext();
        
        assertThat(context.get("x"), is((Object)"a"));
        assertThat(context.get("y"), is((Object)"b"));
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void unparsableContextCausesException(){
        properties.setContextEntries(asList("x="));
        
        properties.getContext();
    }
}
