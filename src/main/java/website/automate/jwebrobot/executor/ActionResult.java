package website.automate.jwebrobot.executor;

public class ActionResult {

    public enum StatusCode {
        SUCCESS,
        FAILURE,
        ERROR
    }

    private boolean failed;

    private StatusCode code;

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

    private void setFailed(boolean failed) {
        this.failed = failed;
    }

    private void setCode(StatusCode code) {
        this.code = code;
    }

    private void setMessage(String message) {
        this.message = message;
    }

    private void setSkipped(boolean skipped) {
        this.skipped = skipped;
    }

    private void setError(Throwable error) {
        this.error = error;
    }

    private void setValue(Object value) {
        this.value = value;
    }

    public static class ActionResultBuilder {

        private boolean failed;

        private StatusCode code;

        private String message;

        private boolean skipped;

        private Throwable error;

        private Object value;

        public ActionResultBuilder withFailed(boolean failed){
            this.failed = failed;
            return this;
        }

        public ActionResultBuilder withCode(StatusCode code){
            this.code = code;
            return this;
        }

        public ActionResultBuilder withMessage(String message){
            this.message = message;
            return this;
        }

        public ActionResultBuilder withSkipped(boolean skipped){
            this.skipped = skipped;
            return this;
        }

        public ActionResultBuilder withError(Throwable error){
            this.error = error;
            return this;
        }

        public ActionResultBuilder withValue(Object value){
            this.value = value;
            return this;
        }

        public ActionResult build(){
            ActionResult result = new ActionResult();

            result.setCode(code);
            result.setFailed(failed);
            result.setMessage(message);
            result.setSkipped(skipped);
            result.setError(error);
            result.setValue(value);

            return result;
        }
    }
}
