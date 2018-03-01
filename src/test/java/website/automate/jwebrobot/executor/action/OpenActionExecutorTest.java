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
import website.automate.waml.io.model.criteria.OpenCriteria;

@RunWith(MockitoJUnitRunner.class)
public class OpenActionExecutorTest {

	private OpenActionExecutor executor = new OpenActionExecutor(null, null, null, null);
	
	@Mock
	private ScenarioExecutionContext context;
	
	@Mock
	private OpenAction action;

	@Mock
	private OpenCriteria criteria;
	
	@Mock
	private WebDriver driver;
	
	@Before
	public void init(){
		when(context.getDriver()).thenReturn(driver);
		when(action.getOpen()).thenReturn(criteria);
	}
	
	@Test
	public void validUrlPassValidation(){
		when(criteria.getUrl()).thenReturn("https://www.wikipedia.de");
		
		executor.perform(action, context);
		
		verify(driver).get("https://www.wikipedia.de");
	}
	
	@Test
	public void missingProtocolDefaultsToHttp(){
		when(criteria.getUrl()).thenReturn("www.wikipedia.de");
		
		executor.perform(action, context);
		
		verify(driver).get("http://www.wikipedia.de");
	}
	
	@Test(expected=MalformedURLException.class)
	public void malformedUrlThrowsException(){
		when(criteria.getUrl()).thenReturn("htttp://wiki pedia.de");
		
		executor.perform(action, context);
	}
}
