package website.automate.jwebrobot.end2end;

import org.junit.runners.Parameterized.Parameters;

import java.util.Collection;

import static java.util.Arrays.asList;

public class ScreenshotOnFailureIT extends End2EndTestCase {

    @Parameters(name = "{0}")
    public static Collection<Object[]> getParameters() {
        return new ParametersBuilder("end2end/screenshot/on_failure/*")
            .withContextBaseUrl("http://testpage")
            .withError(IllegalStateException.class)
            .withFiles(asList("./report/on_failure.yaml_1_ensure.png"))
            .build();
    }
}
