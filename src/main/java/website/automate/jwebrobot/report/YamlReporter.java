package website.automate.jwebrobot.report;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

import website.automate.jwebrobot.context.GlobalExecutionContext;
import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.exceptions.NonReadableFileException;
import website.automate.jwebrobot.listener.ExecutionEventListener;
import website.automate.jwebrobot.model.Action;
import website.automate.jwebrobot.model.Scenario;
import website.automate.jwebrobot.report.model.ActionReport;
import website.automate.jwebrobot.report.model.ExecutionReport;
import website.automate.jwebrobot.report.model.ExecutionStatus;
import website.automate.jwebrobot.report.model.ScenarioReport;

public class YamlReporter implements ExecutionEventListener {

    private String reportPath = "./report.yaml";

    private Yaml yaml = new Yaml();
    
    private Map<Action, Long> actionStartTimeMap = new HashMap<>();
    
    private Map<Action, ActionReport> actionReportMap = new HashMap<>();
    
    private Map<Scenario, ScenarioReport> scenarioReportMap = new LinkedHashMap<>();
    
    @Override
    public void beforeScenario(ScenarioExecutionContext context) {
        Scenario contextScenario = context.getScenario();
        File contextScenarioFile = context.getGlobalContext().getFile(contextScenario);
        
        ScenarioReport report = new ScenarioReport();
        report.setName(contextScenario.getName());
        report.setPath(contextScenarioFile.getAbsolutePath());
        
        scenarioReportMap.put(contextScenario, report);
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
        ScenarioReport scenarioReport = scenarioReportMap.get(context.getRootScenario());
        
        ActionReport actionReport = new ActionReport();
        actionReport.setPath(context.getScenarioInclusionPath());
        actionReport.setName(action.getType().getName());
        
        scenarioReport.getActions().add(actionReport);
        actionStartTimeMap.put(action, System.currentTimeMillis());
        actionReportMap.put(action, actionReport);
    }

    @Override
    public void afterAction(ScenarioExecutionContext context, Action action) {
        ActionReport report = afterActionOrError(context, action);
        report.setStatus(ExecutionStatus.SUCESS);
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
        ExecutionReport report = new ExecutionReport();
        report.setScenarios(new ArrayList<ScenarioReport>(scenarioReportMap.values()));
        report.updateStats();
        writeReport(report);
    }

    @Override
    public void errorExecution(GlobalExecutionContext context,
            Exception exception) {
        ExecutionReport report = afterExecutionOrError(context);
        report.setMessage(exception.getMessage());
        writeReport(report);
    }
    
    private ExecutionReport afterExecutionOrError(GlobalExecutionContext context){
        ExecutionReport report = new ExecutionReport();
        report.setScenarios(new ArrayList<ScenarioReport>(scenarioReportMap.values()));
        report.updateStats();
        return report;
    }
    
    private void writeReport(ExecutionReport report){
        try {
            yaml.dump(report, new FileWriter(reportPath));
        } catch (IOException e) {
            throw new NonReadableFileException(reportPath);
        }
    }
    
    private ActionReport afterActionOrError(ScenarioExecutionContext context, Action action){
        Long startTime = actionStartTimeMap.get(action);
        ActionReport report = actionReportMap.get(action);
        report.setTime(startTime);
        return report;
    }
    
    private ExecutionStatus exceptionToStatus(Exception exception){
        return ExecutionStatus.ERROR;
    }
}
