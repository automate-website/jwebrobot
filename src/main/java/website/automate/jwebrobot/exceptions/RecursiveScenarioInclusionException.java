package website.automate.jwebrobot.exceptions;

import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.model.Scenario;

public class RecursiveScenarioInclusionException extends ScenarioExecutionException {

    private static final long serialVersionUID = -4469843004481555944L;

    private Scenario includedScenario;
    
    public RecursiveScenarioInclusionException(ScenarioExecutionContext context, Scenario includedScenario) {
        super(context);
    }

    public Scenario getIncludedScenario() {
        return includedScenario;
    }
}
