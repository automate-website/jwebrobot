package website.automate.jwebrobot.loader;

import java.util.Collection;
import java.util.List;

public interface ScenarioLoader {

    List<ScenarioFile> load(Collection<String> scenarioPath, String reportPath);
}
