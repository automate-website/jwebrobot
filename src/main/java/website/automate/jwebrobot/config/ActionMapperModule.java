package website.automate.jwebrobot.config;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.Multibinder;

import website.automate.jwebrobot.mapper.action.ClickActionMapper;
import website.automate.jwebrobot.mapper.action.ConditionalActionMapper;
import website.automate.jwebrobot.mapper.action.EnsureActionMapper;
import website.automate.jwebrobot.mapper.action.EnterActionMapper;
import website.automate.jwebrobot.mapper.action.IncludeActionMapper;
import website.automate.jwebrobot.mapper.action.MoveActionMapper;
import website.automate.jwebrobot.mapper.action.OpenActionMapper;
import website.automate.jwebrobot.mapper.action.SelectActionMapper;
import website.automate.jwebrobot.mapper.action.StoreActionMapper;
import website.automate.jwebrobot.mapper.action.WaitActionMapper;
import website.automate.waml.io.model.action.ConditionalAction;


public class ActionMapperModule extends AbstractModule {

    @Override
    protected void configure() {
        Multibinder<ConditionalActionMapper<? extends ConditionalAction>> contextValidatorBinder = Multibinder.newSetBinder(binder(), new TypeLiteral<ConditionalActionMapper<? extends ConditionalAction>>() {});
        
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
