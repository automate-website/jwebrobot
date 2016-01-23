package website.automate.jwebrobot.models.mapper.criteria;

import website.automate.jwebrobot.models.scenario.actions.criteria.ClearCriterion;

public class ClearCriterionMapper extends CriterionMapper<ClearCriterion> {

    @Override
    public String getCriterionName() {
        return ClearCriterion.NAME;
    }

    @Override
    public ClearCriterion map(Object source) {
        ClearCriterion criterion = new ClearCriterion();
        map(source, criterion);

        return criterion;
    }

    @Override
    public void map(Object source, ClearCriterion target) {
        target.setValue((Boolean) source);
    }
}
