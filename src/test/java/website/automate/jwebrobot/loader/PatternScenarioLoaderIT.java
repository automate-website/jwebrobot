package website.automate.jwebrobot.loader;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import website.automate.jwebrobot.AbstractTest;
import website.automate.jwebrobot.ConfigurationProperties;
import website.automate.jwebrobot.exceptions.NonReadableFileException;

import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class PatternScenarioLoaderIT extends AbstractTest {

    @Autowired
    private ScenarioLoader scenarioLoader;
    
    @Test
    public void loadScenariosFromTheBaseDirectoryRecursively(){
        List<ScenarioFile> scenarioFiles = scenarioLoader.load(asList("./src/test/resources/loader"),
                ConfigurationProperties.DEFAULT_REPORT_PATH);
        
        assertThat(scenarioFiles.size(), is(4));
    }
    
    @Test
    public void loadSingleScenarioFromPath(){
        List<ScenarioFile> scenarioFiles = scenarioLoader.load(asList("./src/test/resources/loader/multi.yaml"),
                ConfigurationProperties.DEFAULT_REPORT_PATH);
        
        assertThat(scenarioFiles.size(), is(1));
    }
    
    @Test(expected=NonReadableFileException.class)
    public void failLoadingNonExistingFile(){
        scenarioLoader.load(asList("./src/test/resources/loader/non-existent.yaml"),
                ConfigurationProperties.DEFAULT_REPORT_PATH);
    }
}
