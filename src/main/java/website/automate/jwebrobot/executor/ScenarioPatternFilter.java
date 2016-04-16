package website.automate.jwebrobot.executor;

import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

public class ScenarioPatternFilter {

    public boolean isExecutable(String scenarioPattern, String scenarioName){
        if(StringUtils.isNotBlank(scenarioPattern)){
            return Pattern.matches(scenarioPattern, scenarioName);
        }
        return true;
    }
}
