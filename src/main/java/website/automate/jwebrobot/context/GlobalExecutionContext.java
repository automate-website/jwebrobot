package website.automate.jwebrobot.context;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import website.automate.jwebrobot.executor.ExecutorOptions;
import website.automate.jwebrobot.loader.ScenarioFile;
import website.automate.jwebrobot.model.Scenario;
import website.automate.jwebrobot.utils.PrecedenceComparator;

public class GlobalExecutionContext {

    private Map<String, Scenario> nameScenarioMap = new HashMap<>();

    private Map<Scenario, File> scenarioFileMap = new HashMap<>();
    
    private List<Scenario> scenarios = new ArrayList<>();
    
    private ExecutorOptions options;
    
    public GlobalExecutionContext(Collection<ScenarioFile> scenarioFiles, ExecutorOptions options) {
        this.options = options;
        addScenarios(scenarioFiles);
    }
    
    private void addScenarios(Collection<ScenarioFile> scenarioFiles){
        for(ScenarioFile scenarioFile : scenarioFiles){
            for(Scenario scenario : scenarioFile.getScenarios()){
                addScenario(scenarioFile.getFile(), scenario);
            }
        }
        Collections.sort(scenarios, PrecedenceComparator.getInstance());
    }
    
    private void addScenario(File scenarioFile, Scenario scenario){
        this.scenarioFileMap.put(scenario, scenarioFile);
        this.nameScenarioMap.put(scenario.getName(), scenario);
        this.scenarios.add(scenario);
    }
    
    public Scenario getScenario(String name){
        return nameScenarioMap.get(name);
    }
    
    public File getFile(Scenario scenario){
        return scenarioFileMap.get(scenario);
    }
    
    public ExecutorOptions getOptions() {
        return options;
    }

    public List<Scenario> getScenarios() {
        return scenarios;
    }
}
