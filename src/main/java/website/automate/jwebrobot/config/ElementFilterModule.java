package website.automate.jwebrobot.config;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.multibindings.Multibinder;

import website.automate.jwebrobot.executor.filter.ElementFilter;
import website.automate.jwebrobot.executor.filter.SelectorElementFilter;
import website.automate.jwebrobot.executor.filter.TextElementFilter;
import website.automate.jwebrobot.executor.filter.ValueElementFilter;

public class ElementFilterModule extends AbstractModule {

    @Override
    protected void configure() {
        Multibinder<ElementFilter> listenerBinder = Multibinder.newSetBinder(binder(), ElementFilter.class);
        
        listenerBinder.addBinding().to(SelectorElementFilter.class).in(Singleton.class);
        listenerBinder.addBinding().to(TextElementFilter.class).in(Singleton.class);
        listenerBinder.addBinding().to(ValueElementFilter.class).in(Singleton.class);
    }
}
