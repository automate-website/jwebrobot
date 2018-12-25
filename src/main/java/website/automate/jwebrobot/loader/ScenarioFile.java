package website.automate.jwebrobot.loader;

import website.automate.waml.io.model.main.Scenario;

import java.io.File;

public class ScenarioFile {

    private Scenario scenario;

    private File file;

    public ScenarioFile(Scenario scenario, File file) {
        this.scenario = scenario;
        this.file = file;
    }
    
    public Scenario getScenario() {
        return scenario;
    }

    public File getFile() {
        return file;
    }
}
