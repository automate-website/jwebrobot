package website.automate.jwebrobot.config;

import com.google.inject.AbstractModule;

import website.automate.jwebrobot.expression.ExpressionEvaluator;
import website.automate.jwebrobot.expression.FreemarkerExpressionEvaluator;


public class ExpressionEvaluatorModule extends AbstractModule {

    @Override
    protected void configure() {
        binder().bind(ExpressionEvaluator.class).to(FreemarkerExpressionEvaluator.class);
    }
}
