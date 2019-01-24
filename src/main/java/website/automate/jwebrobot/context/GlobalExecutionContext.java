package website.automate.jwebrobot.context;

import website.automate.jwebrobot.executor.ExecutorOptions;
import website.automate.jwebrobot.loader.ScenarioFile;
import website.automate.waml.io.model.main.Scenario;

import java.io.File;
import java.nio.file.Files;
import java.util.*;

import static java.util.Collections.unmodifiableMap;

public class GlobalExecutionContext {

    private File tempDir = createTempDir();

    private Map<String, Scenario> pathScenarioMap = new HashMap<>();

    private Map<Scenario, File> scenarioFileMap = new HashMap<>();
    
    private Map<String, Object> memory;
    
    private List<Scenario> scenarios = new ArrayList<>();
    
    private ExecutorOptions options;

    private Map<String, Object> exportMemory = new HashMap<>();
    
    public GlobalExecutionContext(Collection<ScenarioFile> scenarioFiles, ExecutorOptions options,
            Map<String, Object> memory) {
        this.options = options;
        this.memory = unmodifiableMap(memory);
        addScenarios(scenarioFiles);
    }
    
    private void addScenarios(Collection<ScenarioFile> scenarioFiles){
        for(ScenarioFile scenarioFile : scenarioFiles){
                addScenario(scenarioFile.getFile(), scenarioFile.getScenario());
        }
    }
    
    private void addScenario(File scenarioFile, Scenario scenario){
        this.scenarioFileMap.put(scenario, scenarioFile);
        this.pathScenarioMap.put(scenario.getPath(), scenario);
        this.scenarios.add(scenario);
    }

    private File createTempDir(){
        try {
            return Files.createTempDirectory("jwebrobot").toFile();
        } catch (Exception e){
            throw new RuntimeException("Can not create temp directory.", e);
        }
    }
    
    public Scenario getScenarioByPath(String name){
        return pathScenarioMap.get(name);
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
    
    public Map<String, Object> getMemory() {
        return memory;
    }

    public File getTempDir() {
        return tempDir;
    }

    public Map<String, Object> getExportMemory() {
        return exportMemory;
    }
}
