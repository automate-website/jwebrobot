package website.automate.jwebrobot.executor;

import website.automate.waml.io.model.main.action.Action;

public class ActionResult {

    public enum StatusCode {
        SUCCESS,
        FAILURE,
        ERROR
    }

    private Action action;

    private boolean failed;

    private StatusCode code = StatusCode.SUCCESS;

    private String message;

    private boolean skipped;

    private Throwable error;

    private Object value;

    public boolean isFailed() {
        return failed;
    }

    public StatusCode getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public boolean isSkipped() {
        return skipped;
    }

    public Throwable getError() {
        return error;
    }

    public Object getValue(){
        return value;
    }

    public void setFailed(boolean failed) {
        this.failed = failed;
    }

    public void setCode(StatusCode code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setSkipped(boolean skipped) {
        this.skipped = skipped;
    }

    public void setError(Throwable error) {
        this.error = error;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }
}
