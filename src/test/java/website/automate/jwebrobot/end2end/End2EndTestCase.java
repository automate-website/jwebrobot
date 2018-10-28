package website.automate.jwebrobot.end2end;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import website.automate.jwebrobot.JWebRobot;

import java.util.List;

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

    @Test
    public void runScenario() {
        expectErrorIfSet();
        runWebRobot();
    }

    private void runWebRobot(){
        JWebRobot.main(arguments.toArray(new String[]{}));
    }

    private void expectErrorIfSet(){
        if(errorClass != null){
            errorRule.expect(errorClass);
        }
    }
}
