package website.automate.jwebrobot.config;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;
import website.automate.jwebrobot.models.mapper.criteria.CriterionMapper;
import website.automate.jwebrobot.models.mapper.criteria.SelectorCriterionMapper;
import website.automate.jwebrobot.models.mapper.criteria.UrlCriterionMapper;


public class CriterionMapperModule extends AbstractModule {

    @Override
    protected void configure() {
        Multibinder<CriterionMapper> criterionMapperBinder = Multibinder.newSetBinder(binder(), CriterionMapper.class);

        criterionMapperBinder.addBinding().to(UrlCriterionMapper.class);
        criterionMapperBinder.addBinding().to(SelectorCriterionMapper.class);

    }
}
