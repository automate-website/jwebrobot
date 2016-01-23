package website.automate.jwebrobot.config;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;
import website.automate.jwebrobot.models.mapper.criteria.*;


public class CriterionMapperModule extends AbstractModule {

    @Override
    protected void configure() {
        Multibinder<CriterionMapper> criterionMapperBinder = Multibinder.newSetBinder(binder(), CriterionMapper.class);

        criterionMapperBinder.addBinding().to(ClearCriterionMapper.class);
        criterionMapperBinder.addBinding().to(IfCriterionMapper.class);
        criterionMapperBinder.addBinding().to(ScenarioCriterionMapper.class);
        criterionMapperBinder.addBinding().to(SelectorCriterionMapper.class);
        criterionMapperBinder.addBinding().to(TextCriterionMapper.class);
        criterionMapperBinder.addBinding().to(TimeCriterionMapper.class);
        criterionMapperBinder.addBinding().to(TimeoutCriterionMapper.class);
        criterionMapperBinder.addBinding().to(UnlessCriterionMapper.class);
        criterionMapperBinder.addBinding().to(UrlCriterionMapper.class);
        criterionMapperBinder.addBinding().to(ValueCriterionMapper.class);

    }
}
