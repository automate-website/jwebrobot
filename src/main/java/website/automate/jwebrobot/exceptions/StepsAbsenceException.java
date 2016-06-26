package website.automate.jwebrobot.exceptions;

import static java.lang.String.format;

import website.automate.jwebrobot.context.ScenarioExecutionContext;

public class StepsAbsenceException extends RuntimeException {

    private static final long serialVersionUID = -8471151278952806816L;

    private static final String MESSAGE = "Steps must be provided in scenario %s.";

    private ScenarioExecutionContext context;
    
    public StepsAbsenceException(ScenarioExecutionContext context) {
        super(format(MESSAGE, context.getScenario().getName()));
        this.context = context;
    }
    
    public ScenarioExecutionContext getContext(){
    	return this.context;
    }
}
