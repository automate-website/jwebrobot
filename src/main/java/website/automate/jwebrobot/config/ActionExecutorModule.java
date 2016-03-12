package website.automate.jwebrobot.config;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.Multibinder;

import website.automate.jwebrobot.executor.action.*;
import website.automate.waml.io.model.action.Action;


public class ActionExecutorModule extends AbstractModule {

    @Override
    protected void configure() {
        Multibinder<ActionExecutor<? extends Action>> actionExecutorBinder = Multibinder.newSetBinder(binder(), new TypeLiteral<ActionExecutor<? extends Action>>() {});

        actionExecutorBinder.addBinding().to(ClickActionExecutor.class);
        actionExecutorBinder.addBinding().to(EnsureActionExecutor.class);
        actionExecutorBinder.addBinding().to(EnterActionExecutor.class);
        actionExecutorBinder.addBinding().to(MoveActionExecutor.class);
        actionExecutorBinder.addBinding().to(OpenActionExecutor.class);
        actionExecutorBinder.addBinding().to(SelectActionExecutor.class);
        actionExecutorBinder.addBinding().to(WaitActionExecutor.class);
        actionExecutorBinder.addBinding().to(IncludeActionExecutor.class);
        actionExecutorBinder.addBinding().to(StoreActionExecutor.class);
    }
}
