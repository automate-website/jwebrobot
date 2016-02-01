package website.automate.jwebrobot.loader;

import website.automate.jwebrobot.model.Scenario;

import java.io.InputStream;
import java.util.List;

public interface ScenarioLoader {

    List<ScenarioFile> load(String scenarioPath);

    List<Scenario> createFromInputStream(InputStream inputStream);
}
