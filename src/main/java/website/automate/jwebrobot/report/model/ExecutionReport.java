package website.automate.jwebrobot.report.model;

import java.util.ArrayList;
import java.util.List;

public class ExecutionReport {

    private ExecutionStatus status = ExecutionStatus.SUCESS;
    
    private String message;
    
    private Double time = 0.0;
    
    private Integer numScenarioTotal = 0;
    
    private Integer numScenarioPasses = 0;
    
    private Integer numScenarioFailures = 0;
    
    private List<ScenarioReport> scenarios = new ArrayList<>();
    
    public void updateStats(){
        for(ScenarioReport scenario : scenarios){
            scenario.updateStats();
            ExecutionStatus scenarioStatus = scenario.getStatus();
            status = ExecutionStatus.worstOf(status, scenarioStatus);
            setNumAction(scenarioStatus);
            time += scenario.getTime();
        }
    }
    
    private void setNumAction(ExecutionStatus actionStatus){
        numScenarioTotal++;
        if(actionStatus == ExecutionStatus.SUCESS){
            numScenarioPasses++;
        } else {
            numScenarioFailures++;
        }
    }
    
    public ExecutionStatus getStatus() {
        return status;
    }

    public void setStatus(ExecutionStatus status) {
        this.status = status;
    }

    public Double getTime() {
        return time;
    }

    public void setTime(Double time) {
        this.time = time;
    }

    public Integer getNumScenarioTotal() {
        return numScenarioTotal;
    }

    public void setNumScenarioTotal(Integer numScenarioTotal) {
        this.numScenarioTotal = numScenarioTotal;
    }

    public Integer getNumScenarioPasses() {
        return numScenarioPasses;
    }

    public void setNumScenarioPasses(Integer numScenarioPasses) {
        this.numScenarioPasses = numScenarioPasses;
    }

    public Integer getNumScenarioFailures() {
        return numScenarioFailures;
    }

    public void setNumScenarioFailures(Integer numScenarioFailures) {
        this.numScenarioFailures = numScenarioFailures;
    }

    public List<ScenarioReport> getScenarios() {
        return scenarios;
    }

    public void setScenarios(List<ScenarioReport> scenarios) {
        this.scenarios = scenarios;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
