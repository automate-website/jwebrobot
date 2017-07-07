package website.automate.jwebrobot.report;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openqa.selenium.UnsupportedCommandException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.Logs;
import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.waml.report.io.WamlReportWriter;
import website.automate.waml.report.io.model.ActionReport;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ReporterTest {

    @InjectMocks
    private Reporter reporter;

    @Mock
    private WamlReportWriter writer;

    @Mock
    private ScenarioExecutionContext context;

    @Mock
    private ActionReport actionReport;

    @Mock
    private WebDriver webDriver;

    @Test
    public void shouldNotThrowErrorIfBrowserLogForwardingIsNotSupportedByTheDriver() {
        when(context.getDriver()).thenReturn(webDriver);
        WebDriver.Options options = mock(WebDriver.Options.class);
        when(webDriver.manage()).thenReturn(options);
        Logs logs = mock(Logs.class);
        when(options.logs()).thenReturn(logs);
        when(logs.get(LogType.BROWSER)).thenThrow(UnsupportedCommandException.class);

        reporter.processLogEntries(context, actionReport);
    }
}
