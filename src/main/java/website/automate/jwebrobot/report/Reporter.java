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
import website.automate.waml.io.mappers.ActionMapper;
import website.automate.waml.io.model.Scenario;
import website.automate.waml.io.model.action.Action;
import website.automate.waml.io.model.report.ActionReport;
import website.automate.waml.io.model.report.ExecutionStatus;
import website.automate.waml.io.model.report.LogEntry;
import website.automate.waml.io.model.report.LogEntry.LogLevel;
import website.automate.waml.io.model.report.ScenarioReport;
import website.automate.waml.io.model.report.WamlReport;
import website.automate.waml.io.writer.WamlWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.*;

@Service
public class Reporter implements ExecutionEventListener {

    private static final Logger LOG = LoggerFactory.getLogger(Reporter.class);

    private WamlWriter writer;

    private ActionMapper actionMapper;

    private Map<Action, Long> actionStartTimeMap = new HashMap<>();

    private Map<Action, Action> actionReportMap = new HashMap<>();

    private Map<Scenario, Scenario> scenarioReportMap = new LinkedHashMap<>();

    @Autowired
    public Reporter(WamlWriter writer, ActionMapper actionMapper) {
        this.writer = writer;
        this.actionMapper = actionMapper;
    }

    @Override
    public void beforeScenario(ScenarioExecutionContext context) {
        if (context.getParent() == null) {
            Scenario contextScenario = context.getScenario();
            File contextScenarioFile = context.getGlobalContext().getFile(contextScenario);

            ScenarioReport report = new ScenarioReport();
            report.setPath(contextScenarioFile.getAbsolutePath());

            Scenario reportScenario = copyScenario(contextScenario);
            reportScenario.setReport(report);

            scenarioReportMap.put(contextScenario, reportScenario);
        }
    }

    @Override
    public void afterScenario(ScenarioExecutionContext context) {
    }

    @Override
    public void errorScenario(ScenarioExecutionContext context, Exception exception) {
        Scenario contextScenario = context.getScenario();

        Scenario reportScenario = scenarioReportMap.get(contextScenario);
        ScenarioReport report = reportScenario.getReport();

        report.setMessage(exception.getMessage());
        report.setStatus(exceptionToStatus(exception));
    }

    @Override
    public void beforeAction(ScenarioExecutionContext context, Action action) {
        Scenario rootScenario = context.getRootScenario();
        Scenario reportScenario = scenarioReportMap.get(rootScenario);

        Action reportAction = actionMapper.map(action);
        ActionReport actionReport = new ActionReport();
        actionReport.setPath(context.getScenarioInclusionPath());
        reportAction.setReport(actionReport);

        reportScenario.getActions().add(reportAction);
        actionStartTimeMap.put(action, System.currentTimeMillis());
        actionReportMap.put(action, reportAction);
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
    public void errorAction(ScenarioExecutionContext context, Action action, Exception exception) {
        ActionReport report = afterActionOrError(context, action);
        report.setStatus(exceptionToStatus(exception));
        report.setMessage(exception.getMessage());
    }

    @Override
    public void beforeExecution(GlobalExecutionContext context) {
    }

    @Override
    public void afterExecution(GlobalExecutionContext context) {
        WamlReport report = new WamlReport();
        report.setScenarios(new ArrayList<Scenario>(scenarioReportMap.values()));
        report.updateStats();
        try {
            writer.write(new FileOutputStream(getReportPath(context)), report);
        } catch (FileNotFoundException e) {
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
        try {
            writer.write(new FileOutputStream(getReportPath(context)), report);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private WamlReport afterExecutionOrError(GlobalExecutionContext context) {
        WamlReport report = new WamlReport();
        report.setScenarios(new ArrayList<Scenario>(scenarioReportMap.values()));
        report.updateStats();
        return report;
    }

    private ActionReport afterActionOrError(ScenarioExecutionContext context, Action action) {
        Long startTime = actionStartTimeMap.get(action);
        Action reportAction = actionReportMap.get(action);
        ActionReport report = reportAction.getReport();

        report.setTime((System.currentTimeMillis() - startTime) / 1000.0);
        return report;
    }

    private ExecutionStatus exceptionToStatus(Exception exception) {
        return ExecutionStatus.ERROR;
    }

    private String getReportPath(GlobalExecutionContext context) {
        return context.getOptions().getReportPath();
    }

    private Scenario copyScenario(Scenario source) {
        Scenario target = new Scenario();
        target.setDescription(source.getDescription());
        target.setName(source.getName());
        target.setFragment(source.getFragment());
        target.setPrecedence(source.getPrecedence());
        target.setTimeout(source.getTimeout());
        target.setUnless(source.getUnless());
        target.setWhen(source.getWhen());
        target.setMeta(source.getMeta());
        return target;
    }
}
