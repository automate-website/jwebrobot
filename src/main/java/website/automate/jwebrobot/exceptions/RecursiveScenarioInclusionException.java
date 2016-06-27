package website.automate.jwebrobot.exceptions;

import static java.text.MessageFormat.format;

import website.automate.waml.io.model.Scenario;

public class RecursiveScenarioInclusionException extends ScenarioExecutionException {

    private static final long serialVersionUID = -4469843004481555944L;

    private Scenario includedScenario;
    
    public RecursiveScenarioInclusionException(String scenarioName) {
        super(format("Scenario {0} is included recursively", scenarioName));
    }

    public Scenario getIncludedScenario() {
        return includedScenario;
    }
}
