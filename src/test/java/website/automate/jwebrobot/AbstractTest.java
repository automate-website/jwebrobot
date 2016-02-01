package website.automate.jwebrobot;


import com.google.inject.Injector;

public abstract class AbstractTest {
    protected Injector injector = GuiceInjector.recreateInstance(GuiceInjector.DEFAULT_MODULES);
}
