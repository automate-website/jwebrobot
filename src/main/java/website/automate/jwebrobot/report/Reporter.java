package website.automate.jwebrobot.report;

import org.openqa.selenium.UnsupportedCommandException;
import org.openqa.selenium.logging.LogType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import website.automate.jwebrobot.context.GlobalExecutionContext;
import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.executor.ExecutorOptions;
import website.automate.jwebrobot.listener.ExecutionEventListener;
import website.automate.waml.io.model.main.Scenario;
import website.automate.waml.io.model.main.action.Action;
import website.automate.waml.io.model.report.*;
import website.automate.waml.io.model.report.LogEntry.LogLevel;
import website.automate.waml.io.writer.WamlWriter;

import java.io.File;
import java.util.*;

@Service
public class Reporter implements ExecutionEventListener {

    private static final Logger LOG = LoggerFactory.getLogger(Reporter.class);

    private WamlWriter writer;

    private Map<Action, Long> actionStartTimeMap = new HashMap<>();

    private Map<Action, ActionReport> actionReportMap = new HashMap<>();

    private Map<Scenario, ScenarioReport> scenarioReportMap = new LinkedHashMap<>();

    @Autowired
    public Reporter(WamlWriter writer) {
        this.writer = writer;
    }

    @Override
    public void beforeScenario(ScenarioExecutionContext context) {
        if (context.getParent() == null) {
            Scenario contextScenario = context.getScenario();
            File contextScenarioFile = context.getGlobalContext().getFile(contextScenario);

            ScenarioReport report = new ScenarioReport();
            report.setSteps(new ArrayList<>());
            report.setPath(contextScenarioFile.getAbsolutePath());

            scenarioReportMap.put(contextScenario, report);
        }
    }

    @Override
    public void afterScenario(ScenarioExecutionContext context) {
    }

    @Override
    public void errorScenario(ScenarioExecutionContext context, Exception exception) {
        Scenario contextScenario = context.getScenario();

        ScenarioReport report = scenarioReportMap.get(contextScenario);
        report.setMessage(exception.getMessage());
        report.setStatus(exceptionToStatus(exception));
    }

    @Override
    public void beforeAction(ScenarioExecutionContext context, Action action) {
        Scenario rootScenario = context.getRootScenario();
        ScenarioReport scenarioReport = scenarioReportMap.get(rootScenario);

        ActionReport actionReport = new ActionReport();
        actionReport.setPath(context.getScenarioInclusionPath());
        actionReport.setStep(action);

        scenarioReport.getSteps().add(actionReport);
        actionStartTimeMap.put(action, System.currentTimeMillis());
        actionReportMap.put(action, actionReport);
    }

    void processLogEntries(ScenarioExecutionContext context, ActionReport actionReport) {
        try {
            List<org.openqa.selenium.logging.LogEntry> logEntries = context.getDriver().manage().logs().get(LogType.BROWSER).getAll();
            ExecutorOptions options = context.getGlobalContext().getOptions();

            // TODO extract mapper
            List<LogEntry> convertedLogEntries = new ArrayList<>();

            for (org.openqa.selenium.logging.LogEntry logEntry : logEntries) {
                LogLevel actualLevel = convertLogLevel(logEntry.getLevel());

                if (LogEntry.isIncluded(options.getBrowserLogLevel(), actualLevel)) {
                    convertedLogEntries.add(new LogEntry(
                        actualLevel, new Date(logEntry.getTimestamp()), logEntry.getMessage()));
                }
            }

            actionReport.setLogEntries(convertedLogEntries);
        } catch (UnsupportedCommandException e) {
            // TODO set flag on the report: https://github.com/automate-website/waml-io/issues/2
            LOG.warn("Current WebDriver does not support browser logging!");
        }
    }

    private LogLevel convertLogLevel(java.util.logging.Level logLevel) {
        if (logLevel == java.util.logging.Level.SEVERE) {
            return LogLevel.ERROR;
        } else if (logLevel == java.util.logging.Level.INFO) {
            return LogLevel.INFO;
        } else if (logLevel == java.util.logging.Level.WARNING) {
            return LogLevel.WARN;
        }
        return LogLevel.DEBUG;
    }

    @Override
    public void afterAction(ScenarioExecutionContext context, Action action) {
        ActionReport report = afterActionOrError(context, action);
        report.setStatus(ExecutionStatus.SUCCESS);
        processLogEntries(context, report);
    }

    @Override
    public void errorAction(ScenarioExecutionContext context, Action action, Throwable error) {
        ActionReport report = afterActionOrError(context, action);

        if(error != null) {
            report.setStatus(exceptionToStatus(error));
            report.setMessage(error.getMessage());
        }
    }

    @Override
    public void beforeExecution(GlobalExecutionContext context) {
    }

    @Override
    public void afterExecution(GlobalExecutionContext context) {
        WamlReport report = new WamlReport();
        report.setScenarios(new ArrayList<ScenarioReport>(scenarioReportMap.values()));
        report.updateStats();
        report.setPath(getReportPath(context));
        report.setExport(context.getExportMemory());
        try {
            writer.write(report);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void errorExecution(GlobalExecutionContext context, Exception exception) {
        WamlReport report = afterExecutionOrError(context);

        String reportMessage = report.getMessage();
        if (reportMessage == null) {
            reportMessage = "";
        }
        reportMessage += exception.getMessage();
        report.setMessage(reportMessage);
        report.setPath(getReportPath(context));
        try {
            writer.write(report);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private WamlReport afterExecutionOrError(GlobalExecutionContext context) {
        WamlReport report = new WamlReport();
        report.setScenarios(new ArrayList<ScenarioReport>(scenarioReportMap.values()));
        report.updateStats();
        return report;
    }

    private ActionReport afterActionOrError(ScenarioExecutionContext context, Action action) {
        Long startTime = actionStartTimeMap.get(action);
        ActionReport report = actionReportMap.get(action);
        report.setTime((System.currentTimeMillis() - startTime) / 1000.0);
        return report;
    }

    private ExecutionStatus exceptionToStatus(Throwable error) {
        return ExecutionStatus.ERROR;
    }

    private String getReportPath(GlobalExecutionContext context) {
        return context.getOptions().getReportPath();
    }
}
