package website.automate.jwebrobot.loader;

import website.automate.waml.io.model.main.Scenario;

import java.io.File;
import java.util.List;

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
