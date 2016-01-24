package website.automate.jwebrobot.loader;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;

import website.automate.jwebrobot.AbstractTest;

public class ScenarioLoaderIT extends AbstractTest {

    private ScenarioLoader scenarioLoader = injector.getInstance(ScenarioLoader.class);
    
    @Test
    public void loadScenariosFromTheBaseDirectoryRecursively(){
        List<ScenarioFile> scenarioFiles = scenarioLoader.load("./src/test/resources/website/automate/jwebrobot/loader");
        
        assertThat(scenarioFiles.size(), is(3));
        assertThat(totalNumberOfScenarios(scenarioFiles), is(4));
    }
    
    @Test
    public void loadSingleScenarioFromPath(){
        List<ScenarioFile> scenarioFiles = scenarioLoader.load("./src/test/resources/website/automate/jwebrobot/loader/multi.yaml");
        
        assertThat(scenarioFiles.size(), is(1));
        assertThat(totalNumberOfScenarios(scenarioFiles), is(2));
    }
    
    @Test(expected=NonReadableFileException.class)
    public void failLoadingNonExistingFile(){
        scenarioLoader.load("./src/test/resources/website/automate/jwebrobot/loader/non-existent.yaml");
    }
    
    private int totalNumberOfScenarios(List<ScenarioFile> scenarioFiles){
        int result = 0;
        for(ScenarioFile scenarioFile : scenarioFiles){
            result+=scenarioFile.getScenarios().size();
        }
        return result;
    }
}
