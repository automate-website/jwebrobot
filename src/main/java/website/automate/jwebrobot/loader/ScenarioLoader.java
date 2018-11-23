package website.automate.jwebrobot.loader;

import website.automate.waml.io.model.main.Scenario;

import java.io.InputStream;
import java.util.Collection;
import java.util.List;

public interface ScenarioLoader {

    List<ScenarioFile> load(Collection<String> scenarioPath, String reportPath);

    List<Scenario> createFromInputStream(InputStream inputStream);
}
