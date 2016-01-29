package website.automate.jwebrobot.config;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;

import website.automate.jwebrobot.validator.ContextValidator;
import website.automate.jwebrobot.validator.MockContextValidator;


public class ContextValidatorModule extends AbstractModule {

    @Override
    protected void configure() {
        Multibinder<ContextValidator> contextValidatorBinder = Multibinder.newSetBinder(binder(), ContextValidator.class);
        
        contextValidatorBinder.addBinding().to(MockContextValidator.class);
    }
}
