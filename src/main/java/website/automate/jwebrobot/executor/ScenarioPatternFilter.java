package website.automate.jwebrobot.executor;

import java.util.regex.Pattern;

public class ScenarioPatternFilter {

    public boolean isExecutable(String scenarioPattern, String scenarioName){
        if(scenarioPattern != null){
            return Pattern.matches(scenarioPattern, scenarioName);
        }
        return true;
    }
}
