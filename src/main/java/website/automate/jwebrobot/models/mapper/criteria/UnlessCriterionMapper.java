package website.automate.jwebrobot.models.mapper.criteria;

import website.automate.jwebrobot.models.scenario.actions.criteria.UnlessCriterion;

public class UnlessCriterionMapper extends CriterionMapper<UnlessCriterion> {

    @Override
    public String getCriterionName() {
        return UnlessCriterion.NAME;
    }

    @Override
    public UnlessCriterion map(Object source) {
        UnlessCriterion criterion = new UnlessCriterion();
        map(source, criterion);

        return criterion;
    }

    @Override
    public void map(Object source, UnlessCriterion target) {
        target.setValue((String) source);
    }
}
