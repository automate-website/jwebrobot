package website.automate.jwebrobot.config;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.Multibinder;

import website.automate.jwebrobot.expression.action.ActionExpressionEvaluator;
import website.automate.jwebrobot.expression.action.ClickActionExpressionEvaluator;
import website.automate.jwebrobot.expression.action.EnsureActionExpressionEvaluator;
import website.automate.jwebrobot.expression.action.EnterActionExpressionEvaluator;
import website.automate.jwebrobot.expression.action.IncludeActionExpressionEvaluator;
import website.automate.jwebrobot.expression.action.MoveActionExpressionEvaluator;
import website.automate.jwebrobot.expression.action.OpenActionExpressionEvaluator;
import website.automate.jwebrobot.expression.action.SelectActionExpressionEvaluator;
import website.automate.jwebrobot.expression.action.StoreActionExpressionEvaluator;
import website.automate.jwebrobot.expression.action.WaitActionExpressionEvaluator;

public class ActionExpressionEvaluatorModule extends AbstractModule {

    @Override
    protected void configure() {
        Multibinder<ActionExpressionEvaluator<?>> contextValidatorBinder = Multibinder.newSetBinder(binder(), new TypeLiteral<ActionExpressionEvaluator<?>>() {});
        
        contextValidatorBinder.addBinding().to(ClickActionExpressionEvaluator.class);
        contextValidatorBinder.addBinding().to(EnterActionExpressionEvaluator.class);
        contextValidatorBinder.addBinding().to(MoveActionExpressionEvaluator.class);
        contextValidatorBinder.addBinding().to(IncludeActionExpressionEvaluator.class);
        contextValidatorBinder.addBinding().to(EnsureActionExpressionEvaluator.class);
        contextValidatorBinder.addBinding().to(OpenActionExpressionEvaluator.class);
        contextValidatorBinder.addBinding().to(WaitActionExpressionEvaluator.class);
        contextValidatorBinder.addBinding().to(StoreActionExpressionEvaluator.class);
        contextValidatorBinder.addBinding().to(SelectActionExpressionEvaluator.class);
    }
}
