package website.automate.jwebrobot.loader;

import java.io.File;
import java.util.List;

import website.automate.jwebrobot.model.Scenario;

public class ScenarioFile {

    private List<Scenario> scenarios;
    
    private File file;

    public ScenarioFile(List<Scenario> scenarios, File file) {
        this.scenarios = scenarios;
        this.file = file;
    }
    
    public List<Scenario> getScenarios() {
        return scenarios;
    }

    public File getFile() {
        return file;
    }
}
