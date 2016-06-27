package website.automate.jwebrobot.executor.action;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openqa.selenium.WebDriver;

import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.exceptions.MalformedURLException;
import website.automate.waml.io.model.action.OpenAction;

@RunWith(MockitoJUnitRunner.class)
public class OpenActionExecutorTest {

	private OpenActionExecutor executor = new OpenActionExecutor(null, null, null, null);
	
	@Mock
	private ScenarioExecutionContext context;
	
	@Mock
	private OpenAction action;
	
	@Mock
	private WebDriver driver;
	
	@Before
	public void init(){
		when(context.getDriver()).thenReturn(driver);
	}
	
	@Test
	public void validUrlPassValidation(){
		when(action.getUrl()).thenReturn("https://www.wikipedia.de");
		
		executor.perform(action, context);
		
		verify(driver).get("https://www.wikipedia.de");
	}
	
	@Test
	public void missingProtocolDefaultsToHttp(){
		when(action.getUrl()).thenReturn("www.wikipedia.de");
		
		executor.perform(action, context);
		
		verify(driver).get("http://www.wikipedia.de");
	}
	
	@Test(expected=MalformedURLException.class)
	public void malformedUrlThrowsException(){
		when(action.getUrl()).thenReturn("htttp://wiki pedia.de");
		
		executor.perform(action, context);
	}
}
