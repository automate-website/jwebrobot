package website.automate.jwebrobot.executor;

import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class ScenarioPatternFilter {

    public boolean isExecutable(String scenarioPattern, String scenarioName){
        if(scenarioPattern != null && !scenarioPattern.equals("")){
            return Pattern.matches(scenarioPattern, scenarioName);
        }
        return true;
    }
}
