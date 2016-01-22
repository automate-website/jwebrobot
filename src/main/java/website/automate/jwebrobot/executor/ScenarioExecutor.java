package website.automate.jwebrobot.executor;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import website.automate.jwebrobot.models.scenario.Scenario;
import website.automate.jwebrobot.models.scenario.actions.Action;

import java.util.Collections;
import java.util.List;

public class ScenarioExecutor {

    private final static PrecedenceComparator precedenceComparator = new PrecedenceComparator();

    private final ContextHolder context;
    private final ExecutorOptions executorOptions;

    public ScenarioExecutor(ContextHolder contextHolder, ExecutorOptions executorOptions) {
        this.context = contextHolder;
        this.executorOptions = executorOptions;
    }

    public ExecutionResults execute(List<Scenario> scenarios) {
        Collections.sort(scenarios, precedenceComparator);

        for (Scenario scenario : scenarios) {
            processScenario(scenario, context);
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
