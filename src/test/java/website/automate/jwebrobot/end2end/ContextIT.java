package website.automate.jwebrobot.end2end;

import org.junit.runners.Parameterized.Parameters;

import java.util.Collection;

public class ContextIT extends End2EndTestCase {

    @Parameters(name = "{0}")
    public static Collection<Object[]> getParameters() {
        return new ParametersBuilder("end2end/context")
            .withContextBaseUrl("http://testpage")
            .withContextEntry("url", "http://testpage")
            .withContextEntry("timeout", "1")
            .withContextEntry("isEnabled", "false")
            .withScanBaseDirForDependencies(true)
            .build();
    }
}
