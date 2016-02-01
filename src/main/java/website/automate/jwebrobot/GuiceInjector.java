package website.automate.jwebrobot;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.util.Modules;
import website.automate.jwebrobot.config.*;
import website.automate.jwebrobot.config.logger.LoggerModule;

public class GuiceInjector {
    public static final Module DEFAULT_MODULES = Modules.combine(
        new LoggerModule(),
        new ActionExecutorModule(),
        new ExpressionEvaluatorModule(),
        new ContextValidatorModule(),
        new ExecutionEventListenerModule(),
        new ScenarioExecutorModule(),
        new ScenarioLoaderModule()
    );

    private static Injector INSTANCE = null;

    public static Injector getInstance() {
        if (INSTANCE == null) {
            INSTANCE = Guice.createInjector(DEFAULT_MODULES);
        }

        return INSTANCE;
    }

    public static Injector recreateInstance(Module modules) {
        INSTANCE = Guice.createInjector(
            modules
        );

        return INSTANCE;
    }
}
