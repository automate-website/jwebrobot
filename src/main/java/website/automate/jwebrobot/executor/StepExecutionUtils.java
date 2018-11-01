package website.automate.jwebrobot.executor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StepExecutionUtils {

    private TimeoutResolver timeoutResolver;

    @Autowired
    public StepExecutionUtils(TimeoutResolver timeoutResolver){
        this.timeoutResolver = timeoutResolver;
    }

    public TimeoutResolver getTimeoutResolver() {
        return timeoutResolver;
    }
}
