package website.automate.jwebrobot.report;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import website.automate.jwebrobot.exceptions.ActionExecutorMissingException;

@RunWith(MockitoJUnitRunner.class)
public class ExceptionReportMessageTranslatorFactoryTest {

	private ExceptionReportMessageTranslatorFactory factory;
	
	@Mock
	private ExceptionReportMessageTranslator<Exception> defaultTranslator;
	
	@Mock
	private ExceptionReportMessageTranslator<ActionExecutorMissingException> actionExecutorMissingTranslator;
	
	@SuppressWarnings("unchecked")
	@Test
	public void defaultTranslatorUsedIfNoSpecificAvailable(){
		Set<ExceptionReportMessageTranslator<? extends Throwable>> translators = new HashSet<>();
		translators.add(defaultTranslator);
		when(defaultTranslator.getSupportedType()).thenReturn(Exception.class);
		factory = new ExceptionReportMessageTranslatorFactory(translators);
		
		ExceptionReportMessageTranslator<? extends Throwable> translator = factory.getInstance(ActionExecutorMissingException.class);
		
		assertThat((ExceptionReportMessageTranslator<Exception>)translator, is(defaultTranslator));
	}
	
	@SuppressWarnings("unchecked")
	public void specificTranslatorIsUsedIfAvailable(){
		Set<ExceptionReportMessageTranslator<? extends Throwable>> translators = new HashSet<>();
		translators.add(defaultTranslator);
		translators.add(actionExecutorMissingTranslator);
		when(defaultTranslator.getSupportedType()).thenReturn(Exception.class);
		when(actionExecutorMissingTranslator.getSupportedType()).thenReturn(ActionExecutorMissingException.class);
		factory = new ExceptionReportMessageTranslatorFactory(translators);
		
		ExceptionReportMessageTranslator<? extends Throwable> translator = factory.getInstance(ActionExecutorMissingException.class);
		
		assertThat((ExceptionReportMessageTranslator<ActionExecutorMissingException>)translator, is(actionExecutorMissingTranslator));
	}
}
