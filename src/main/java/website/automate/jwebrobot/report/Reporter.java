package website.automate.jwebrobot.report;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.google.inject.Inject;

import website.automate.jwebrobot.context.ExceptionContext;
import website.automate.jwebrobot.context.GlobalExecutionContext;
import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.listener.ExecutionEventListener;
import website.automate.waml.io.model.Scenario;
import website.automate.waml.io.model.action.Action;
import website.automate.waml.report.io.WamlReportWriter;
import website.automate.waml.report.io.model.ActionReport;
import website.automate.waml.report.io.model.ExecutionStatus;
import website.automate.waml.report.io.model.ScenarioReport;
import website.automate.waml.report.io.model.SimpleActionReport;
import website.automate.waml.report.io.model.SimpleScenarioReport;
import website.automate.waml.report.io.model.WamlReport;

public class Reporter implements ExecutionEventListener {

    private static final String DEFAULT_REPORT_PATH = "./report.yaml";

    private WamlReportWriter writer;
    
    private ExceptionReportMessageTranslatorFactory translatorFactory;
    
    private Map<Action, Long> actionStartTimeMap = new HashMap<>();
    
    private Map<Action, ActionReport> actionReportMap = new HashMap<>();
    
    private Map<Scenario, ScenarioReport> scenarioReportMap = new LinkedHashMap<>();
    
    @Inject
    public Reporter(WamlReportWriter writer, ExceptionReportMessageTranslatorFactory translatorFactory) {
        this.writer = writer;
        this.translatorFactory = translatorFactory;
    }
    
    @Override
    public void beforeScenario(ScenarioExecutionContext context) {
        Scenario contextScenario = context.getScenario();
        File contextScenarioFile = context.getGlobalContext().getFile(contextScenario);
        
        ScenarioReport report = new SimpleScenarioReport();
        report.setScenario(copyScenario(contextScenario));
        report.setPath(contextScenarioFile.getAbsolutePath());
        
        scenarioReportMap.put(contextScenario, report);
    }

    @Override
    public void afterScenario(ScenarioExecutionContext context) {
    }

    @Override
    public void errorScenario(ScenarioExecutionContext context, Exception exception) {
    	ExceptionReportMessageTranslator<? extends Throwable> translator  = getTranslator(exception);
        Scenario contextScenario = context.getScenario();
        ExceptionContext exceptionContext = new ExceptionContext(context);
        
        ScenarioReport report = scenarioReportMap.get(contextScenario);
        report.setMessage(translator.translateToMessage(exception, exceptionContext));
        report.setStatus(translator.translateToStatus(exception, exceptionContext));
    }

    @Override
    public void beforeAction(ScenarioExecutionContext context, Action action) {
        ScenarioReport scenarioReport = scenarioReportMap.get(context.getRootScenario());
        
        ActionReport actionReport = new SimpleActionReport();
        actionReport.setPath(context.getScenarioInclusionPath());
        actionReport.setAction(action);
        
        scenarioReport.getSteps().add(actionReport);
        actionStartTimeMap.put(action, System.currentTimeMillis());
        actionReportMap.put(action, actionReport);
    }

    @Override
    public void afterAction(ScenarioExecutionContext context, Action action) {
        ActionReport report = afterActionOrError(context, action);
        report.setStatus(ExecutionStatus.SUCCESS);
    }

    @Override
    public void errorAction(ScenarioExecutionContext context, Action action, Exception exception) {
    	ExceptionReportMessageTranslator<? extends Throwable> translator  = getTranslator(exception);
    	ExceptionContext exceptionContext = new ExceptionContext(context, action);
    	
        ActionReport report = afterActionOrError(context, action);
        report.setStatus(translator.translateToStatus(exception, exceptionContext));
        report.setMessage(translator.translateToMessage(exception, exceptionContext));
    }

    @Override
    public void beforeExecution(GlobalExecutionContext context) {
    }

    @Override
    public void afterExecution(GlobalExecutionContext context) {
        WamlReport report = new WamlReport();
        report.setScenarios(new ArrayList<ScenarioReport>(scenarioReportMap.values()));
        report.updateStats();
        try {
            writer.write(new FileOutputStream(getReportPath(context)), report);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void errorExecution(GlobalExecutionContext context,
            Exception exception) {
    	ExceptionReportMessageTranslator<? extends Throwable> translator  = getTranslator(exception);
        WamlReport report = afterExecutionOrError(context);
        ExceptionContext exceptionContext = new ExceptionContext(context);
    	
        report.setMessage(translator.translateToMessage(exception, exceptionContext));
        report.setMessage(translator.translateToMessage(exception, exceptionContext));
        try {
            writer.write(new FileOutputStream(getReportPath(context)), report);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    
    private WamlReport afterExecutionOrError(GlobalExecutionContext context){
        WamlReport report = new WamlReport();
        report.setScenarios(new ArrayList<ScenarioReport>(scenarioReportMap.values()));
        report.updateStats();
        return report;
    }
    
    private ActionReport afterActionOrError(ScenarioExecutionContext context, Action action){
        Long startTime = actionStartTimeMap.get(action);
        ActionReport report = actionReportMap.get(action);
        report.setTime((System.currentTimeMillis() - startTime) / 1000.0);
        return report;
    }
    
    private String getReportPath(GlobalExecutionContext context){
        String reportPath = context.getOptions().getReportPath();
        if(reportPath != null){
            return reportPath;
        }
        return DEFAULT_REPORT_PATH;
    }
    
    private Scenario copyScenario(Scenario source){
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
    
    private ExceptionReportMessageTranslator<? extends Throwable> getTranslator(Throwable e){
    	return translatorFactory.getInstance(e.getClass());
    }
}
