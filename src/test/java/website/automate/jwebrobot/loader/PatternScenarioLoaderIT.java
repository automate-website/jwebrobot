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
import website.automate.jwebrobot.exceptions.NonReadableFileException;
import website.automate.waml.io.WamlDeserializationException;
import website.automate.waml.io.model.Scenario;
import website.automate.waml.io.model.action.Action;
import website.automate.waml.io.model.action.ClickAction;
import website.automate.waml.io.model.action.OpenAction;

public class PatternScenarioLoaderIT extends AbstractTest {

    private ScenarioLoader scenarioLoader = injector.getInstance(ScenarioLoader.class);
    
    @Test
    public void loadScenariosFromTheBaseDirectoryRecursively(){
        List<ScenarioFile> scenarioFiles = scenarioLoader.load("./src/test/resources/website/automate/jwebrobot/loader");
        
        assertThat(scenarioFiles.size(), is(3));
        assertThat(totalNumberOfScenarios(scenarioFiles), is(4));
    }
    
    @Test
    public void loadSingleScenarioFromPath(){
        List<ScenarioFile> scenarioFiles = scenarioLoader.load("./src/test/resources/website/automate/jwebrobot/loader/multi.waml");
        
        assertThat(scenarioFiles.size(), is(1));
        assertThat(totalNumberOfScenarios(scenarioFiles), is(2));
    }
    
    @Test(expected=NonReadableFileException.class)
    public void failLoadingNonExistingFile(){
        scenarioLoader.load("./src/test/resources/website/automate/jwebrobot/loader/non-existent.waml");
    }
    
    @Test(expected = WamlDeserializationException.class)
    public void shouldThrowUnknownActionException() {
        InputStream stream = getSystemResourceAsStream("./failing_scenarios/unknown-action.waml");

        scenarioLoader.createFromInputStream(stream);
    }

    @Test(expected = WamlDeserializationException.class)
    public void shouldThrowTooManyActionsException() {
        InputStream stream = getSystemResourceAsStream("./failing_scenarios/too-many-actions.waml");

        scenarioLoader.createFromInputStream(stream);
    }

    @Test
    public void scenarioListShouldBeLoaded() throws IOException {
        // given
        InputStream inputStream = getSystemResourceAsStream("./scenarios/multi-document.waml");

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

        OpenAction openAction1 = OpenAction.class.cast(steps1.get(0));
        assertThat(openAction1.getUrl(), is("www.example.com"));
        
        ClickAction clickAction1 = ClickAction.class.cast(steps1.get(1));
        assertThat(clickAction1.getSelector(), is("button[type=submit]"));

        OpenAction openAction2 = OpenAction.class.cast(steps2.get(0));
        assertThat(openAction2.getUrl(), is("www.example.com"));
        
        OpenAction openAction3 = OpenAction.class.cast(steps2.get(1));
        assertThat(openAction3.getUrl(), is("www.example2.com"));
        
        ClickAction clickAction2 = ClickAction.class.cast(steps2.get(2));
        assertThat(clickAction2.getSelector(), is("button[type=submit2]"));

    }
    
    private int totalNumberOfScenarios(List<ScenarioFile> scenarioFiles){
        int result = 0;
        for(ScenarioFile scenarioFile : scenarioFiles){
            result+=scenarioFile.getScenarios().size();
        }
        return result;
    }
}
