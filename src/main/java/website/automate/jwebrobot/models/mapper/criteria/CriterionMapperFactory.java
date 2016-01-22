package website.automate.jwebrobot.models.mapper.criteria;

import website.automate.jwebrobot.exceptions.UnknownCriterionException;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Set;

public class CriterionMapperFactory {
    private final Set<CriterionMapper> criterionMappers;
    private HashMap<String, CriterionMapper> criterionMapperByCriterionNameMap = new HashMap<>();

    @Inject
    public CriterionMapperFactory(Set<CriterionMapper> criterionMappers) {
        this.criterionMappers = criterionMappers;
        init();
    }

    private void init() {
        for (CriterionMapper criterionMapper : criterionMappers) {
            criterionMapperByCriterionNameMap.put(criterionMapper.getCriterionName().toLowerCase(), criterionMapper);
        }
    }

    public CriterionMapper getInstance(String criterionName) {
        CriterionMapper criterionMapper = criterionMapperByCriterionNameMap.get(criterionName.toLowerCase());
        if (criterionMapper == null) {
            throw new UnknownCriterionException(criterionName);
        }

        return criterionMapper;
    }
}
