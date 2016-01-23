package website.automate.jwebrobot.models.mapper.actions;

import website.automate.jwebrobot.exceptions.UnknownCriterionException;
import website.automate.jwebrobot.models.mapper.criteria.CriterionMapper;
import website.automate.jwebrobot.models.mapper.criteria.CriterionMapperFactory;
import website.automate.jwebrobot.models.scenario.actions.Action;
import website.automate.jwebrobot.models.scenario.actions.criteria.Criterion;
import website.automate.jwebrobot.utils.Mapper;

import javax.inject.Inject;
import java.util.Map;

public abstract class ActionMapper<T extends Action> implements Mapper<Object, T> {

    @Inject
    private CriterionMapperFactory criterionMapperFactory;

    public abstract String getActionName();

    @Override
    public void map(Object source, T target) {
        if (source instanceof Map) {
            Map<String, Object> criteria = (Map<String, Object>) source;

            for (String criterionName : criteria.keySet()) {
                Object criteriaValue = criteria.get(criterionName);
                if (criteriaValue instanceof String) {
                    CriterionMapper<Criterion> criterionMapper = criterionMapperFactory.getInstance(criterionName);
                    if (criterionMapper == null) {
                        throw new UnknownCriterionException(criterionName);
                    }

                    target.putCriterion(criterionMapper.map(criteriaValue));
                } else {
                    // TODO complex criteria
                    throw new RuntimeException("not yet supported");
                }
            }
        } else {
            String defaultCriterionName = target.getDefaultCriterionName();
            CriterionMapper<Criterion> criterionMapper = criterionMapperFactory.getInstance(defaultCriterionName);
            if (criterionMapper == null) {
                throw new UnknownCriterionException(defaultCriterionName);
            }

            target.putCriterion(criterionMapper.map(source));
        }
    }
}
