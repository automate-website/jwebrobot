package website.automate.jwebrobot.config;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.Multibinder;
import website.automate.jwebrobot.mapper.action.*;
import website.automate.waml.io.model.action.ConditionalAction;


public class ActionMapperModule extends AbstractModule {

    @Override
    protected void configure() {
        Multibinder<ConditionalActionMapper<? extends ConditionalAction>> contextValidatorBinder = Multibinder.newSetBinder(binder(), new TypeLiteral<ConditionalActionMapper<? extends ConditionalAction>>() {});

        contextValidatorBinder.addBinding().to(AlertActionMapper.class);
        contextValidatorBinder.addBinding().to(ClickActionMapper.class);
        contextValidatorBinder.addBinding().to(EnterActionMapper.class);
        contextValidatorBinder.addBinding().to(MoveActionMapper.class);
        contextValidatorBinder.addBinding().to(EnsureActionMapper.class);
        contextValidatorBinder.addBinding().to(IncludeActionMapper.class);
        contextValidatorBinder.addBinding().to(OpenActionMapper.class);
        contextValidatorBinder.addBinding().to(WaitActionMapper.class);
        contextValidatorBinder.addBinding().to(StoreActionMapper.class);
        contextValidatorBinder.addBinding().to(SelectActionMapper.class);
    }
}
