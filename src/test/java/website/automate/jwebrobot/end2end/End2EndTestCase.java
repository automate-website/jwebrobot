package website.automate.jwebrobot.end2end;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import website.automate.jwebrobot.JWebRobot;

import java.io.File;
import java.text.MessageFormat;
import java.util.List;

import static java.text.MessageFormat.format;
import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public abstract class End2EndTestCase {

    @Rule
    public ExpectedException errorRule = ExpectedException.none();

    @Parameter(0)
    public String testName;

    @Parameter(1)
    public List<String> arguments;

    @Parameter(2)
    public Class<Throwable> errorClass;

    @Parameter(3)
    public List<String> files;

    @Test
    public void runScenario() {
        expectErrorIfSet();

        try {
            runWebRobot();
        } catch(Exception e){
            verifyFilesExistIfSet();
            throw e;
        }
    }

    private void runWebRobot(){
        JWebRobot.main(arguments.toArray(new String[]{}));
    }

    private void expectErrorIfSet(){
        if(errorClass != null){
            errorRule.expect(errorClass);
        }
    }

    private void verifyFilesExistIfSet(){
        if(files != null){
            files.stream().forEach(this::verifyFileExists);
        }
    }

    private void verifyFileExists(String fileName){
        File file = new File(fileName);
        assertTrue(format("File {0} doesn't exist!", fileName), file.exists());
    }
}
