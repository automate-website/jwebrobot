package website.automate.jwebrobot.report.model;

import java.util.ArrayList;
import java.util.List;

public class ScenarioReport {

    private String name;
    
    private String message;
    
    private String path;

    private ExecutionStatus status = ExecutionStatus.SUCESS;

    private Double time = 0.0;

    private Integer numActionPasses = 0;

    private Integer numActionFailures = 0;

    private List<ActionReport> actions = new ArrayList<>();
    
    public void updateStats(){
        for(ActionReport action : actions){
            ExecutionStatus actionStatus = action.getStatus();
            status = ExecutionStatus.worstOf(status, actionStatus);
            setNumAction(actionStatus);
            time += action.getTime();
        }
    }
    
    private void setNumAction(ExecutionStatus actionStatus){
        if(actionStatus == ExecutionStatus.SUCESS){
            this.numActionPasses++;
        } else {
            this.numActionFailures++;
        }
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
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

    public Integer getNumActionPasses() {
        return numActionPasses;
    }

    public void setNumActionPasses(Integer numActionPasses) {
        this.numActionPasses = numActionPasses;
    }

    public Integer getNumActionFailures() {
        return numActionFailures;
    }

    public void setNumActionFailures(Integer numActionFailures) {
        this.numActionFailures = numActionFailures;
    }

    public List<ActionReport> getActions() {
        return actions;
    }

    public void setActions(List<ActionReport> actions) {
        this.actions = actions;
    }
}
