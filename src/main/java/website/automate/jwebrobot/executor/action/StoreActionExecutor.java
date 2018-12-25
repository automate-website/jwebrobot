package website.automate.jwebrobot.executor.action;

import org.springframework.stereotype.Service;
import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.executor.ActionExecutorUtils;
import website.automate.jwebrobot.executor.ActionResult;
import website.automate.waml.io.model.main.action.DefineAction;

import java.util.Map;
import java.util.Map.Entry;

@Service
public class StoreActionExecutor extends BaseActionExecutor<DefineAction> {

    @Override
    public void execute(DefineAction action,
                        ScenarioExecutionContext context,
                        ActionResult result,
                        ActionExecutorUtils utils) {
        Map<String, Object> memory = context.getMemory();

        Map<String, Object> criteriaValueMap = action.getDefine().getFacts();

        for(Entry<String, Object> criteriaValueEntry : criteriaValueMap.entrySet()){
            memory.put(criteriaValueEntry.getKey(), criteriaValueEntry.getValue());
        }
    }

    @Override
    public Class<DefineAction> getSupportedType() {
        return DefineAction.class;
    }

}
