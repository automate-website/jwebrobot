package website.automate.jwebrobot.report;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openqa.selenium.UnsupportedCommandException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.Logs;
import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.waml.report.io.WamlReportWriter;
import website.automate.waml.report.io.model.ActionReport;

import java.util.List;
import java.util.logging.Level;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.*;

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

    @Mock
    private WebDriver.Options options;

    @Mock
    private Logs logs;

    @Mock
    private website.automate.jwebrobot.context.GlobalExecutionContext globalContext;

    @Mock
    private website.automate.jwebrobot.executor.ExecutorOptions executorOptions;

    @Captor
    private ArgumentCaptor<List<website.automate.waml.report.io.model.LogEntry>> logEntryListCaptor;

    @Before
    public void setUpContext() {
        when(context.getDriver()).thenReturn(webDriver);
        when(webDriver.manage()).thenReturn(options);
        when(options.logs()).thenReturn(logs);

        when(context.getGlobalContext()).thenReturn(globalContext);
        when(globalContext.getOptions()).thenReturn(executorOptions);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void shouldNotThrowErrorIfBrowserLogForwardingIsNotSupportedByTheDriver() {
        when(logs.get(LogType.BROWSER)).thenThrow(UnsupportedCommandException.class);

        reporter.processLogEntries(context, actionReport);
    }

    @Test
    public void shouldConvertLogEntries() {
        LogEntries logEntries = mock(LogEntries.class);
        when(logs.get(LogType.BROWSER)).thenReturn(logEntries);
        LogEntry logEntry = mock(LogEntry.class);
        when(logEntries.getAll()).thenReturn(asList(logEntry));

        when(logEntry.getLevel()).thenReturn(Level.FINEST);
        when(executorOptions.getBrowserLogLevel()).thenReturn(website.automate.waml.report.io.model.LogEntry.LogLevel.DEBUG);

        reporter.processLogEntries(context, actionReport);

        verify(logEntries).getAll();

        verify(actionReport).setLogEntries(logEntryListCaptor.capture());
        List<website.automate.waml.report.io.model.LogEntry> logEntryList = logEntryListCaptor.getValue();
        assertThat(logEntryList, hasSize(1));
    }
}
