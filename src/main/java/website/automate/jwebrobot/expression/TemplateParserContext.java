package website.automate.jwebrobot.expression;

import org.springframework.expression.ParserContext;
import org.springframework.stereotype.Service;

@Service
public class TemplateParserContext implements ParserContext {

    public String getExpressionPrefix() {
        return "${";
    }

    public String getExpressionSuffix() {
        return "}";
    }

    public boolean isTemplate() {
        return true;
    }
}
