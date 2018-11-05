package website.automate.jwebrobot.executor.action;

import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.executor.ActionExecutorUtils;
import website.automate.jwebrobot.executor.ActionResult;
import website.automate.waml.io.model.action.StoreAction;

import java.util.Map;
import java.util.Map.Entry;

import org.springframework.stereotype.Service;

@Service
public class StoreActionExecutor implements ActionExecutor<StoreAction> {

    @Override
    public ActionResult execute(final StoreAction action,
                                final ScenarioExecutionContext context,
                                final ActionExecutorUtils utils) {
        Map<String, Object> memory = context.getMemory();

        Map<String, String> criteriaValueMap = action.getFacts();

        for(Entry<String, String> criteriaValueEntry : criteriaValueMap.entrySet()){
            memory.put(criteriaValueEntry.getKey(), criteriaValueEntry.getValue());
        }

        return null;
    }

    @Override
    public Class<StoreAction> getSupportedType() {
        return StoreAction.class;
    }

}
