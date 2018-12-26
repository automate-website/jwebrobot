package website.automate.jwebrobot.executor;

import website.automate.waml.io.model.main.action.Action;

public class ActionResult {

    public static final String SUCCESS = "success";

    public static final String FAILURE = "failure";

    public static final String ERROR = "error";

    private Action rawAction;

    private Action evaluatedAction;

    private boolean failed;

    private String code = SUCCESS;

    private String message;

    private boolean skipped;

    private Throwable error;

    private Object value;

    public boolean isFailed() {
        return failed;
    }

    public String getCode() {
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

    public void setCode(String code) {
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

    public Action getRawAction() {
        return rawAction;
    }

    public void setRawAction(Action rawAction) {
        this.rawAction = rawAction;
    }

    public Action getEvaluatedAction() {
        return evaluatedAction;
    }

    public void setEvaluatedAction(Action evaluatedAction) {
        this.evaluatedAction = evaluatedAction;
    }

    public Action getEvaluatedOrRawAction(){
        Action action = getEvaluatedAction();
        if(action == null){
            action = getRawAction();
        }
        return action;
    }
}
