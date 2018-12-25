package website.automate.jwebrobot.executor.action;

import org.springframework.stereotype.Service;
import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.executor.ActionExecutionResult.ActionResultBuilder;
import website.automate.jwebrobot.executor.ActionExecutorUtils;
import website.automate.jwebrobot.executor.ActionExecutionResult;
import website.automate.waml.io.model.main.action.DefineAction;

import java.util.Map;
import java.util.Map.Entry;

@Service
public class StoreActionExecutor implements ActionExecutor<DefineAction> {

    @Override
    public ActionExecutionResult execute(final DefineAction action,
                                         final ScenarioExecutionContext context,
                                         final ActionExecutorUtils utils) {
        final ActionResultBuilder resultBuilder = new ActionResultBuilder();
        Map<String, Object> memory = context.getMemory();

        Map<String, Object> criteriaValueMap = action.getDefine().getFacts();

        for(Entry<String, Object> criteriaValueEntry : criteriaValueMap.entrySet()){
            memory.put(criteriaValueEntry.getKey(), criteriaValueEntry.getValue());
        }

        return resultBuilder
            .build();
    }

    @Override
    public Class<DefineAction> getSupportedType() {
        return DefineAction.class;
    }

}
