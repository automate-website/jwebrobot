package website.automate.jwebrobot.config;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;

import website.automate.jwebrobot.listener.ExecutionEventListener;
import website.automate.jwebrobot.listener.MockExecutionEventListener;

public class ExecutionEventListenerModule extends AbstractModule {

    @Override
    protected void configure() {
        Multibinder<ExecutionEventListener> listenerBinder = Multibinder.newSetBinder(binder(), ExecutionEventListener.class);
        
        listenerBinder.addBinding().to(MockExecutionEventListener.class);
    }
}
