package website.automate.jwebrobot.executor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.expression.ExpressionEvaluator;
import website.automate.waml.io.model.Scenario;

@Service
public class ScenarioPreprocessor {

    private final ExpressionEvaluator expressionEvaluator;
    
    @Autowired
    public ScenarioPreprocessor(ExpressionEvaluator expressionEvaluator) {
        this.expressionEvaluator = expressionEvaluator;
    }
    
    public Scenario preprocess(Scenario scenario, ScenarioExecutionContext context){
        Scenario preprocessedScenario = new Scenario();
        preprocessedScenario.setDescription(scenario.getDescription());
        preprocessedScenario.setFragment(scenario.getFragment());
        preprocessedScenario.setName(scenario.getName());
        preprocessedScenario.setActions(scenario.getActions());
        preprocessedScenario.setPrecedence(scenario.getPrecedence());
        
        preprocessedScenario.setMeta(scenario.getMeta());
        
        preprocessedScenario.setWhen(preprocessProperty(scenario.getWhen(), context));
        preprocessedScenario.setUnless(preprocessProperty(scenario.getUnless(), context));
        preprocessedScenario.setTimeout(preprocessProperty(scenario.getTimeout(), context));
        return preprocessedScenario;
    }
    
    private String preprocessProperty(String value, ScenarioExecutionContext context){
        if(value == null){
            return null;
        }
        return expressionEvaluator.evaluate(value, context.getTotalMemory());
    }
}
