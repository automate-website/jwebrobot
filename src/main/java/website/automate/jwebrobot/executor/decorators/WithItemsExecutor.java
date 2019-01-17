package website.automate.jwebrobot.executor.decorators;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.executor.action.StepExecutor;
import website.automate.jwebrobot.expression.SpelExpressionEvaluator;
import website.automate.waml.io.model.main.action.Action;

import java.util.Collections;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Service
public class WithItemsExecutor {

    private StepExecutor stepExecutor;

    private SpelExpressionEvaluator expressionEvaluator;

    private ObjectMapper objectMapper;

    @Autowired
    public WithItemsExecutor(@Lazy StepExecutor stepExecutor,
                             SpelExpressionEvaluator expressionEvaluator,
                             ObjectMapper objectMapper){
        this.stepExecutor = stepExecutor;
        this.expressionEvaluator = expressionEvaluator;
        this.objectMapper = objectMapper;
    }

    public boolean isApply(Action action){
        Object withItems = action.getWithItems();
        if(withItems instanceof String){
            return isNotBlank((String)withItems);
        }
        return withItems != null;
    }

    public void execute(Action action, ScenarioExecutionContext context){
        Iterable<Object> items = evaluate(action.getWithItems(), context);

        action.setWithItems(null);

        for(Object item : items){
            Action copy = deepCopy(action);
            context.getMemory().put("item", item);
            stepExecutor.execute(copy, context);
        }
    }

    private Action deepCopy(Action action){
        try {
            return objectMapper.readValue(objectMapper.writeValueAsString(action), Action.class);
        } catch(Exception e){
            throw new RuntimeException("Can not copy action.", e);
        }
    }

    private Iterable<Object> evaluate(Object withItems, ScenarioExecutionContext context){
        if(withItems == null){
            return Collections.emptyList();
        }
        if(withItems instanceof String){
            String withItemsStr = String.class.cast(withItems);

            if(isBlank(withItemsStr)){
                return Collections.emptyList();
            }

            return asCollection(expressionEvaluator.evaluate("${" + withItemsStr + "}",
                context.getTotalMemory(),
                Object.class));
        }

        return asCollection(withItems);
    }

    @SuppressWarnings("unchecked")
    private Iterable<Object> asCollection(Object value){
        if(value instanceof Iterable){
            return (Iterable)value;
        }
        throw new RuntimeException("Can not iterate over the given items");
    }
}
