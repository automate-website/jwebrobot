package website.automate.jwebrobot;

import static java.text.MessageFormat.format;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.beust.jcommander.Parameter;

public class ConfigurationProperties {

    @Parameter(names = "-scenarioPath", description = "Path to a single WAML scenario or WAML project root directory.", required = true)
    private String scenarioPath;

    @Parameter(names = "-browser", description = "Browser to use for execution (e.g firefox or chrome).", required = false)
    private String browser = "firefox";
    
    @Parameter(names = "-context", required = false)
    private List<String> contextEntries;

    public String getScenarioPath() {
        return scenarioPath;
    }

    public void setScenarioPath(String scenarioPath) {
        this.scenarioPath = scenarioPath;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }
    
    public Map<String, String> getContext(){
        Map<String, String> contextMap = new HashMap<>();
        for(String contextEntry : contextEntries){
            String [] contextPair = contextEntry.split("=");
            if(contextPair.length != 2){
                throw new IllegalArgumentException(format("Context entry {0} must contain = separator.", contextEntry));
            }
            contextMap.put(contextPair[0], contextPair[1]);
        }
        return contextMap;
    }

    public void setContextEntries(List<String> contextEntries) {
        this.contextEntries = contextEntries;
    }
}
