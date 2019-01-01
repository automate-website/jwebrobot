package website.automate.jwebrobot;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.codearte.jfairy.Fairy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import website.automate.waml.io.WamlConfig;
import website.automate.waml.io.reader.WamlReader;
import website.automate.waml.io.writer.WamlWriter;

@Configuration
public class Config {

    @Bean
    public WamlReader wamlReader() {
        return WamlConfig.getInstance().getWamlReader();
    }

    @Bean
    public WamlWriter wamlReportWriter() {
        return WamlConfig.getInstance().getWamlWriter();
    }

    @Bean
    public ObjectMapper wamlObjectMapper(){
        return WamlConfig.getInstance().getMapper();
    }

    @Bean
    public ExpressionParser expressionParser() {
        return new SpelExpressionParser();
    }

    @Bean
    public Fairy mock(){
        return Fairy.create();
    }
}
