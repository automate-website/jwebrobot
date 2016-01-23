package website.automate.jwebrobot.config;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;
import website.automate.jwebrobot.models.mapper.actions.*;


public class ActionMapperModule extends AbstractModule {
    @Override
    protected void configure() {
        Multibinder<ActionMapper> actionMapperBinder = Multibinder.newSetBinder(binder(), ActionMapper.class);

        actionMapperBinder.addBinding().to(ClickActionMapper.class);
        actionMapperBinder.addBinding().to(EnsureActionMapper.class);
        actionMapperBinder.addBinding().to(EnterActionMapper.class);
        actionMapperBinder.addBinding().to(IncludeActionMapper.class);
        actionMapperBinder.addBinding().to(MoveActionMapper.class);
        actionMapperBinder.addBinding().to(OpenActionMapper.class);
        actionMapperBinder.addBinding().to(SelectActionMapper.class);
        actionMapperBinder.addBinding().to(WaitActionMapper.class);

    }
}
