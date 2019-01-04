package website.automate.jwebrobot.end2end;

import org.junit.runners.Parameterized.Parameters;

import java.util.Collection;

public class StepIT extends End2EndTestCase {

    @Parameters(name = "{0}")
    public static Collection<Object[]> getParameters() {
        return new ParametersBuilder("end2end/step/*")
            .withContextBaseUrl("http://testpage")
            .build();
    }
}
