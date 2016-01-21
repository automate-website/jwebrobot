package website.automate.jwebrobot.executor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import website.automate.jwebrobot.models.scenario.actions.Action;

public class ActionExecutorFactory {

    private Map<Class<? extends Action>, ActionExecutor<? extends Action>> executorRegistry = new HashMap<>(); 

    private List<ActionExecutor<? extends Action>> executors = new ArrayList<>();
    
    public ActionExecutorFactory(){
        init();
    }
    
    private void init(){
        executors.add(new OpenActionExecutor());
        
        for(ActionExecutor<?> executor : executors){
            executorRegistry.put(executor.getActionType(), executor);
        }
    }
    
    public ActionExecutor<?> getInstance(Class<? extends Action> actionType){
        return executorRegistry.get(actionType);
    }
}
