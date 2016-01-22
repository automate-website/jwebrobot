package website.automate.jwebrobot.config;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;
import website.automate.jwebrobot.models.mapper.actions.ActionMapper;
import website.automate.jwebrobot.models.mapper.actions.ClickActionMapper;
import website.automate.jwebrobot.models.mapper.actions.EnsureActionMapper;
import website.automate.jwebrobot.models.mapper.actions.OpenActionMapper;


public class ActionMapperModule extends AbstractModule {
    @Override
    protected void configure() {
        Multibinder<ActionMapper> actionMapperBinder = Multibinder.newSetBinder(binder(), ActionMapper.class);

        actionMapperBinder.addBinding().to(ClickActionMapper.class);
        actionMapperBinder.addBinding().to(OpenActionMapper.class);
        actionMapperBinder.addBinding().to(EnsureActionMapper.class);

    }
}
