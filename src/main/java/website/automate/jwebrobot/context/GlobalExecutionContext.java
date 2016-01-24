package website.automate.jwebrobot.context;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import website.automate.jwebrobot.models.scenario.Scenario;

public class GlobalExecutionContext {

    private Map<String, Scenario> scenarios = new HashMap<>();

    public GlobalExecutionContext(Collection<Scenario> scenarios) {
        addScenarios(scenarios);
    }
    
    private void addScenarios(Collection<Scenario> scenarios){
        for(Scenario scenario : scenarios){
            addScenario(scenario);
        }
        
    }
    
    private void addScenario(Scenario scenario){
        this.scenarios.put(scenario.getName(), scenario);
    }
    
    public Scenario getScenario(String name){
        return scenarios.get(name);
    }
}
