package website.automate.jwebrobot.context;

import org.openqa.selenium.WebDriver;

import website.automate.jwebrobot.model.Scenario;

import java.util.Map;
import java.util.UUID;

public class ScenarioExecutionContext {

    private String sessionId;
    
    private Scenario scenario;

    private WebDriver driver;

    private Map<String, Object> memory;
    
    private ScenarioExecutionContext parent;
    
    private GlobalExecutionContext globalContext;

    private long timeout = 1;

    public ScenarioExecutionContext(GlobalExecutionContext globalContext,
            Scenario scenario,
            WebDriver driver, Map<String, Object> memory) {
        this.globalContext = globalContext;
        this.scenario = scenario;
        this.driver = driver;
        this.memory = memory;
        this.sessionId = UUID.randomUUID().toString();
    }
    
    public ScenarioExecutionContext createChildContext(Scenario scenario){
        return new ScenarioExecutionContext(getGlobalContext(), scenario, getDriver(), getMemory(), this);
    }
    
    private ScenarioExecutionContext(GlobalExecutionContext globalContext,
            Scenario scenario,
            WebDriver driver, Map<String, Object> memory,
            ScenarioExecutionContext parent) {
        this(globalContext, scenario, driver, memory);
        this.parent = parent;
        this.sessionId = parent.getRoot().getSessionId();
    }
    
    public Scenario getScenario() {
        return scenario;
    }
    
    public WebDriver getDriver() {
        return driver;
    }

    public Map<String, Object> getMemory() {
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
    
    public String getScenarioInclusionPath(){
        String result = scenario.getName();
        if(parent != null){
            return parent.getScenarioInclusionPath() + "/" + result; 
        }
        return result;
    }
    
    public Scenario getRootScenario(){
        return getRoot().getScenario();
    }
    
    public ScenarioExecutionContext getRoot(){
        if(parent == null){
            return this;
        }
        return parent.getRoot();
    }
    
    public boolean containsScenario(Scenario scenario){
        if(this.getScenario().equals(scenario)){
            return true;
        }
        ScenarioExecutionContext parentContext = this.getParent();
        if(parentContext == null){
            return false;
        }
        return parentContext.containsScenario(scenario);
    }

    public String getSessionId() {
        return sessionId;
    }
}
