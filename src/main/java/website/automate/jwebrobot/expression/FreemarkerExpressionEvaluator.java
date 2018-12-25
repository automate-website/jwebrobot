package website.automate.jwebrobot.expression;

import freemarker.cache.NullCacheStorage;
import freemarker.template.*;
import io.codearte.jfairy.Fairy;
import org.springframework.stereotype.Service;
import website.automate.jwebrobot.exceptions.ExpressionEvaluationException;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class FreemarkerExpressionEvaluator implements ExpressionEvaluator {

    private Configuration config = createConfig();
    
    private Fairy mock = Fairy.create();
    
    @Override
    public String evaluate(String expression, Map<String, Object> memory) {
        Map<String, Object> context = new HashMap<>();
        context.putAll(memory);
        context.put("_", Collections.singletonMap("mock", mock));
        
        try {
            Template template = new Template("expression", expression, this.config);
            StringWriter resultWriter = new StringWriter();
            template.process(context, resultWriter);
            
            return resultWriter.toString();
        } catch (IOException | TemplateException e) {
            throw new ExpressionEvaluationException(expression);
        }
    }
    
    private Configuration createConfig(){
        Configuration config = new Configuration(new Version(2, 3, 23));
        config.setDefaultEncoding("UTF-8");
        config.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        config.setCacheStorage(NullCacheStorage.INSTANCE);
        return config;
    }
}
