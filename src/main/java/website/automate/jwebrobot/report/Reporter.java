package website.automate.jwebrobot.report;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.google.inject.Inject;

import website.automate.jwebrobot.context.GlobalExecutionContext;
import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.listener.ExecutionEventListener;
import website.automate.jwebrobot.report.model.ActionReport;
import website.automate.jwebrobot.report.model.ExecutionReport;
import website.automate.jwebrobot.report.model.ExecutionStatus;
import website.automate.jwebrobot.report.model.ScenarioReport;
import website.automate.waml.io.model.ActionType;
import website.automate.waml.io.model.Scenario;
import website.automate.waml.io.model.action.Action;

public class Reporter implements ExecutionEventListener {

    private String reportPath = "./report";

    private ReportWriter writer;
    
    private Map<Action, Long> actionStartTimeMap = new HashMap<>();
    
    private Map<Action, ActionReport> actionReportMap = new HashMap<>();
    
    private Map<Scenario, ScenarioReport> scenarioReportMap = new LinkedHashMap<>();
    
    @Inject
    public Reporter(ReportWriter writer) {
        this.writer = writer;
    }
    
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
        actionReport.setName(ActionType.findByClazz(action.getClass()).getName());
        
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
        writer.writeReport(reportPath, report);
    }

    @Override
    public void errorExecution(GlobalExecutionContext context,
            Exception exception) {
        ExecutionReport report = afterExecutionOrError(context);
        report.setMessage(exception.getMessage());
        writer.writeReport(reportPath, report);
    }
    
    private ExecutionReport afterExecutionOrError(GlobalExecutionContext context){
        ExecutionReport report = new ExecutionReport();
        report.setScenarios(new ArrayList<ScenarioReport>(scenarioReportMap.values()));
        report.updateStats();
        return report;
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
