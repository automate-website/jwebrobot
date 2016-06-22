package website.automate.jwebrobot.context;

import org.openqa.selenium.WebDriver;

import website.automate.waml.io.model.Scenario;
import website.automate.waml.io.model.action.Action;
import website.automate.waml.io.model.action.IncludeAction;

import java.util.HashMap;
import java.util.Map;

public class ScenarioExecutionContext {

    private Integer stepCount = 0;
    
    private Scenario scenario;

    private WebDriver driver;

    private Map<String, Object> memory = new HashMap<>();
    
    private ScenarioExecutionContext parent;
    
    private GlobalExecutionContext globalContext;

    public ScenarioExecutionContext(GlobalExecutionContext globalContext,
            Scenario scenario,
            WebDriver driver) {
        this.globalContext = globalContext;
        this.scenario = scenario;
        this.driver = driver;
    }
    
    public ScenarioExecutionContext createChildContext(Scenario scenario){
        return new ScenarioExecutionContext(getGlobalContext(), scenario, getDriver(), getMemory(), this);
    }
    
    private ScenarioExecutionContext(GlobalExecutionContext globalContext,
            Scenario scenario,
            WebDriver driver, Map<String, Object> memory,
            ScenarioExecutionContext parent) {
        this(globalContext, scenario, driver);
        this.memory = memory;
        this.parent = parent;
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
    
    public Map<String, Object> getTotalMemory(){
    	Map<String, Object> totalMemory = new HashMap<>();
    	totalMemory.putAll(memory);
    	totalMemory.putAll(getGlobalContext().getMemory());
    	return totalMemory;
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
    
    public void countStep(Action action){
        if(!(action instanceof IncludeAction)){
            this.stepCount++;
            if(this.getParent() != null){
                this.getParent().countStep(action);
            }
        }
    }

    public void setScenario(Scenario scenario) {
        this.scenario = scenario;
    }

    public Integer getStepCount() {
        return stepCount;
    }
}
