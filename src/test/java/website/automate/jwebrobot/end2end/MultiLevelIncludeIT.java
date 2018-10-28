package website.automate.jwebrobot.end2end;

import org.junit.runners.Parameterized.Parameters;

import java.util.Collection;

public class MultiLevelIncludeIT extends End2EndTestCase {

    @Parameters(name = "{0}")
    public static Collection<Object[]> getParameters() {
        return new ParametersBuilder("end2end/multi-level-include/first-level-component.yaml")
            .withContextBaseUrl("http://testpage")
            .withScanBaseDirForDependencies(true)
            .build();
    }
}
