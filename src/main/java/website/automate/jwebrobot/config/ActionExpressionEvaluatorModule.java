package website.automate.jwebrobot.config;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.Multibinder;
import website.automate.jwebrobot.expression.action.*;

public class ActionExpressionEvaluatorModule extends AbstractModule {

    @Override
    protected void configure() {
        Multibinder<ActionExpressionEvaluator<?>> contextValidatorBinder = Multibinder.newSetBinder(binder(), new TypeLiteral<ActionExpressionEvaluator<?>>() {});

        contextValidatorBinder.addBinding().to(AlertActionExpressionEvaluator.class);
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
