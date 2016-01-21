package website.automate.jwebrobot.models.mapper.actions;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;


public class ActionMapperModule extends AbstractModule {
    @Override
    protected void configure() {
        Multibinder<ActionMapper> actionMapperBinder = Multibinder.newSetBinder(binder(), ActionMapper.class);

        actionMapperBinder.addBinding().to(ClickActionMapper.class);
        actionMapperBinder.addBinding().to(OpenActionMapper.class);

    }
}
