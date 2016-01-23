package website.automate.jwebrobot.models.mapper.criteria;

import website.automate.jwebrobot.models.scenario.actions.criteria.IfCriterion;

public class IfCriterionMapper extends CriterionMapper<IfCriterion> {

    @Override
    public String getCriterionName() {
        return IfCriterion.NAME;
    }

    @Override
    public IfCriterion map(Object source) {
        IfCriterion criterion = new IfCriterion();
        map(source, criterion);

        return criterion;
    }

    @Override
    public void map(Object source, IfCriterion target) {
        target.setValue((String) source);
    }
}
