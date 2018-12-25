package website.automate.jwebrobot.executor;

import org.springframework.stereotype.Service;
import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.waml.io.model.main.action.TimeLimitedAction;

@Service
public class TimeoutResolver {

    static final long DEFAULT_TIMEOUT_S = 1;

    public Long resolve(TimeLimitedAction action, ScenarioExecutionContext context){
            Long optionsTimeout = context.getGlobalContext().getOptions().getTimeout();

            if(optionsTimeout != null){
                return optionsTimeout;
            }

            String actionTimeout = action.getTimeout();
            if(actionTimeout != null){
                return Long.parseLong(actionTimeout);
            }

            return DEFAULT_TIMEOUT_S;
    }
}
