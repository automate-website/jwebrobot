package website.automate.jwebrobot.report.model;

public class ActionReport {

    private String name;

    private String path;

    private String message;
    
    private Double time = 0.0;

    private ExecutionStatus status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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
    
    public void setTime(Long startTimeInMillis){
        this.time = (System.currentTimeMillis() - startTimeInMillis) / 1000.0; 
    }
}
