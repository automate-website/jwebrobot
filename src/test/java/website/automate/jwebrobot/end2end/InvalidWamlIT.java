package website.automate.jwebrobot.end2end;

import org.junit.runners.Parameterized.Parameters;
import website.automate.waml.io.WamlDeserializationException;

import java.util.Collection;

public class InvalidWamlIT extends End2EndTestCase {

    @Parameters(name = "{0}")
    public static Collection<Object[]> getParameters() {
        return new ParametersBuilder("end2end/invalid-waml")
            .withError(IllegalStateException.class)
            .build();
    }
}
