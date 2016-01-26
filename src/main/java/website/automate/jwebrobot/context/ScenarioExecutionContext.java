package website.automate.jwebrobot.context;

import org.openqa.selenium.WebDriver;

import website.automate.jwebrobot.model.Scenario;

import java.util.Map;

public class ScenarioExecutionContext {

    private Scenario scenario;

    private WebDriver driver;

    private Map<String, String> memory;
    
    private ScenarioExecutionContext parent;
    
    private GlobalExecutionContext globalContext;

    private long timeout = 1;

    public ScenarioExecutionContext(GlobalExecutionContext globalContext,
            Scenario scenario,
            WebDriver driver, Map<String, String> memory) {
        this.globalContext = globalContext;
        this.scenario = scenario;
        this.driver = driver;
        this.memory = memory;
    }
    
    public ScenarioExecutionContext createChildContext(Scenario scenario){
        return new ScenarioExecutionContext(getGlobalContext(), scenario, getDriver(), getMemory(), this);
    }
    
    private ScenarioExecutionContext(GlobalExecutionContext globalContext,
            Scenario scenario,
            WebDriver driver, Map<String, String> memory,
            ScenarioExecutionContext parent) {
        this(globalContext, scenario, driver, memory);
        this.parent = parent;
    }
    
    public Scenario getScenario() {
        return scenario;
    }
    
    public WebDriver getDriver() {
        return driver;
    }

    public Map<String, String> getMemory() {
        return memory;
    }

    public long getTimeout() {
        return timeout;
    }

    public ScenarioExecutionContext getParent() {
        return parent;
    }

    public GlobalExecutionContext getGlobalContext() {
        return globalContext;
    }
}
