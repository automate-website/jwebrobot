package website.automate.jwebrobot.executor;

import java.util.regex.Pattern;
import org.springframework.stereotype.Service;

@Service
public class ScenarioPatternFilter {

    public boolean isExecutable(String scenarioPattern, String scenarioName){
        if(scenarioPattern != null && !scenarioPattern.equals("")){
            return Pattern.matches(scenarioPattern, scenarioName);
        }
        return true;
    }
}
