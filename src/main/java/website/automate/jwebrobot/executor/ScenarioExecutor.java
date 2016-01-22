package website.automate.jwebrobot.executor;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import website.automate.jwebrobot.models.scenario.Scenario;
import website.automate.jwebrobot.models.scenario.actions.Action;

import javax.inject.Inject;
import java.util.Collections;
import java.util.List;

public class ScenarioExecutor {
    @Inject
    private PrecedenceComparator precedenceComparator;

    public ScenarioExecutor() {}

    public ExecutionResults execute(List<Scenario> scenarios, ContextHolder context, ExecutorOptions executorOptions) {
        Collections.sort(scenarios, precedenceComparator);

        for (Scenario scenario : scenarios) {
            processScenario(scenario, context);
        }

        return null;
    }

    private void processScenario(Scenario scenario, ContextHolder context) {
        WebDriver driver = new FirefoxDriver();
        for (Action action : scenario.getSteps()) {
            processStep(action, driver);
            //action.getExecutor(driver);
        }
        driver.quit();
    }

    private void processStep(Action action, WebDriver driver) {

    }
}
