package website.automate.jwebrobot.exceptions;

import website.automate.jwebrobot.context.ScenarioExecutionContext;

public class ScenarioExecutionException extends RuntimeException {

    private static final long serialVersionUID = 5235547246946029749L;
    
    private ScenarioExecutionContext context;
    
    public ScenarioExecutionException(ScenarioExecutionContext context){
        super();
        this.context = context;
    }

    public ScenarioExecutionContext getContext() {
        return context;
    }
}
