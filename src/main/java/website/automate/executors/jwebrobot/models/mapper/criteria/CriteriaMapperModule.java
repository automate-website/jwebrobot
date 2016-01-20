package website.automate.executors.jwebrobot.models.mapper.criteria;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;


public class CriteriaMapperModule extends AbstractModule {
    @Override
    protected void configure() {
        Multibinder<CriterionMapper> criterionMapperBinder = Multibinder.newSetBinder(binder(), CriterionMapper.class);

        criterionMapperBinder.addBinding().to(OpenCriterionMapper.class);
        criterionMapperBinder.addBinding().to(SelectorCriterionMapper.class);
        // TODO
    }
}
