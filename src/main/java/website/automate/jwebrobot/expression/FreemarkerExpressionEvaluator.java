package website.automate.jwebrobot.expression;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

import website.automate.jwebrobot.exceptions.ExpressionEvaluationException;
import freemarker.cache.NullCacheStorage;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.Version;

public class FreemarkerExpressionEvaluator implements ExpressionEvaluator {

    private Configuration config = createConfig();
    
    @Override
    public Object evaluate(String expression, Map<String, Object> memory) {
        try {
            Template template = new Template("expression", expression, this.config);
            StringWriter resultWriter = new StringWriter();
            template.process(memory, resultWriter);
            
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
