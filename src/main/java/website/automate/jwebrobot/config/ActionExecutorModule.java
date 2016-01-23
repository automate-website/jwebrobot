package website.automate.jwebrobot.config;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;
import website.automate.jwebrobot.executor.action.*;


public class ActionExecutorModule extends AbstractModule {

    @Override
    protected void configure() {
        Multibinder<ActionExecutor> actionExecutorBinder = Multibinder.newSetBinder(binder(), ActionExecutor.class);

        actionExecutorBinder.addBinding().to(ClickActionExecutor.class);
        actionExecutorBinder.addBinding().to(EnsureActionExecutor.class);
        actionExecutorBinder.addBinding().to(EnterActionExecutor.class);
        actionExecutorBinder.addBinding().to(MoveActionExecutor.class);
        actionExecutorBinder.addBinding().to(OpenActionExecutor.class);
        actionExecutorBinder.addBinding().to(SelectActionExecutor.class);
        actionExecutorBinder.addBinding().to(WaitActionExecutor.class);

    }
}
