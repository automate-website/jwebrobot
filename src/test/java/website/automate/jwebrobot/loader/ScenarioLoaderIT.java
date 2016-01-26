package website.automate.jwebrobot.loader;

import static java.lang.ClassLoader.getSystemResourceAsStream;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.junit.Test;

import website.automate.jwebrobot.AbstractTest;
import website.automate.jwebrobot.exceptions.TooManyActionsException;
import website.automate.jwebrobot.exceptions.UnknownActionException;
import website.automate.jwebrobot.model.Action;
import website.automate.jwebrobot.model.Scenario;

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
    
    @Test(expected = UnknownActionException.class)
    public void shouldThrowUnknownActionException() {
        InputStream stream = getSystemResourceAsStream("./failing_scenarios/unknown-action.yaml");

        scenarioLoader.createFromInputStream(stream);
    }

    @Test(expected = TooManyActionsException.class)
    public void shouldThrowTooManyActionsException() {
        InputStream stream = getSystemResourceAsStream("./failing_scenarios/too-many-actions.yaml");

        scenarioLoader.createFromInputStream(stream);
    }

    @Test
    public void scenarioListShouldBeLoaded() throws IOException {
        // given
        InputStream inputStream = getSystemResourceAsStream("./scenarios/multi-document.yaml");

        // when
        List<Scenario> scenarioList = scenarioLoader.createFromInputStream(inputStream);

        // then
        assertThat(scenarioList, hasSize(2));

        Scenario scenario1 = scenarioList.get(0),
                scenario2 = scenarioList.get(1);

        assertThat(scenario1.getName(), is("Scenario A"));
        assertThat(scenario2.getName(), is("Scenario B"));

        List<Action> steps1 = scenario1.getSteps();
        assertThat(steps1, hasSize(2));
        List<Action> steps2 = scenario2.getSteps();
        assertThat(steps2, hasSize(3));

        assertThat(steps1.get(0).getUrl(), is("www.example.com"));
        assertThat(steps1.get(1).getSelector(), is("button[type=submit]"));

        assertThat(steps2.get(0).getUrl(), is("www.example.com"));
        assertThat(steps2.get(1).getUrl(), is("www.example2.com"));
        assertThat(steps2.get(2).getSelector(), is("button[type=submit2]"));

    }
    
    private int totalNumberOfScenarios(List<ScenarioFile> scenarioFiles){
        int result = 0;
        for(ScenarioFile scenarioFile : scenarioFiles){
            result+=scenarioFile.getScenarios().size();
        }
        return result;
    }
}
