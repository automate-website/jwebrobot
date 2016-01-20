package website.automate.executors.jwebrobot.models.mapper.actions;

import com.google.inject.Inject;
import website.automate.executors.jwebrobot.exceptions.UnknownCriterionException;
import website.automate.executors.jwebrobot.models.mapper.criteria.CriterionMapper;
import website.automate.executors.jwebrobot.models.scenario.actions.Action;
import website.automate.executors.jwebrobot.models.scenario.actions.criteria.Criterion;
import website.automate.executors.jwebrobot.utils.Mapper;

import java.util.Map;
import java.util.Set;

public abstract class ActionMapper<T extends Action> implements Mapper<Object, T> {

    @Inject
    private Set<CriterionMapper> existingCriteria;

    public abstract String getActionName();

    @Override
    public void map(Object source, T target) {
        if (source instanceof String) {
            String defaultCriterionName = target.getDefaultCriterionName();
            CriterionMapper<Criterion> criterionMapper = findCriterionMapperByCriterionName(defaultCriterionName);
            if (criterionMapper == null) {
                throw new UnknownCriterionException(defaultCriterionName);
            }

            target.putCriterion(criterionMapper.map(source));
        } else {
            Map<String, Object> criteria = (Map<String, Object>) source;

            for (String criterionName : criteria.keySet()) {
                Object criteriaValue = criteria.get(criterionName);
                if (criteriaValue instanceof String) {
                    CriterionMapper<Criterion> criterionMapper = findCriterionMapperByCriterionName(criterionName);
                    if (criterionMapper == null) {
                        throw new UnknownCriterionException(criterionName);
                    }

                    target.putCriterion(criterionMapper.map(criteriaValue));
                } else {
                    // TODO complex criteria
                    throw new RuntimeException("not yet supported");
                }
            }
        }
    }

    /**
     * TODO use a map
     * @param criterionName
     * @return
     */
    private CriterionMapper findCriterionMapperByCriterionName(String criterionName) {
        for (CriterionMapper criterionMapper : existingCriteria) {
            if (criterionMapper.getCriterionName().equalsIgnoreCase(criterionName)) {
                return criterionMapper;
            }
        }

        return null;
    }
}
