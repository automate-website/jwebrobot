package website.automate.jwebrobot.executor;

import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.executor.filter.ElementFilterChain;
import website.automate.waml.io.model.action.ElementStoreAction;
import website.automate.waml.io.model.action.FilterAction;
import website.automate.waml.io.model.action.StoreAction;

import java.util.List;
import java.util.Map;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Service
public class ElementStorage {

    public <T extends ElementStoreAction> void store(T action,
                                                     ScenarioExecutionContext context,
                                                     WebElement webElement){
        String storeKey = action.getStore();

        if(isNotBlank(storeKey) && webElement != null){
            Map<String, Object> memory = context.getMemory();
            memory.put(storeKey, webElement);
        }
    }
}
