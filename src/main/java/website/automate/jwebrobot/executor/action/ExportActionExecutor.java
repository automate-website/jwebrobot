package website.automate.jwebrobot.executor.action;

import org.springframework.stereotype.Service;
import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.executor.ActionExecutorUtils;
import website.automate.jwebrobot.executor.ActionResult;
import website.automate.waml.io.model.main.action.ExportAction;

import java.util.Map;
import java.util.Map.Entry;

@Service
public class ExportActionExecutor extends BaseActionExecutor<ExportAction> {

    @Override
    public void execute(ExportAction action,
                        ScenarioExecutionContext context,
                        ActionResult result,
                        ActionExecutorUtils utils) {
        Map<String, Object> memory = context.getGlobalContext().getExportMemory();

        Map<String, Object> criteriaValueMap = action.getExport().getFacts();

        for(Entry<String, Object> criteriaValueEntry : criteriaValueMap.entrySet()){
            memory.put(criteriaValueEntry.getKey(), criteriaValueEntry.getValue());
        }
    }

    @Override
    public Class<ExportAction> getSupportedType() {
        return ExportAction.class;
    }

}
