package website.automate.jwebrobot.loader;

import java.io.InputStream;
import java.util.Collection;
import java.util.List;

import website.automate.waml.io.model.Scenario;

public interface ScenarioLoader {

    List<ScenarioFile> load(Collection<String> scenarioPath, String reportPath);

    List<Scenario> createFromInputStream(InputStream inputStream);
}
