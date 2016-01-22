package website.automate.jwebrobot.executor;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import website.automate.jwebrobot.models.scenario.Scenario;
import website.automate.jwebrobot.models.scenario.actions.Action;

import javax.inject.Inject;
import java.util.Collections;
import java.util.List;

public class ScenarioExecutor {

    private final PrecedenceComparator precedenceComparator;

    @Inject
    public ScenarioExecutor(PrecedenceComparator precedenceComparator) {
        this.precedenceComparator = precedenceComparator;
    }

    public ExecutionResults execute(List<Scenario> scenarios, ContextHolder contextHolder, ExecutorOptions executorOptions) {
        Collections.sort(scenarios, precedenceComparator);

        for (Scenario scenario : scenarios) {
            processScenario(scenario, contextHolder);
        }

        return null;
    }

    private void processScenario(Scenario scenario, ContextHolder contextHolder) {
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
