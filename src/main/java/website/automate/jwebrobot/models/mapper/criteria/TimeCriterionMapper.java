package website.automate.jwebrobot.models.mapper.criteria;

import website.automate.jwebrobot.models.scenario.actions.criteria.TimeCriterion;

public class TimeCriterionMapper extends CriterionMapper<TimeCriterion> {

    @Override
    public String getCriterionName() {
        return TimeCriterion.NAME;
    }

    @Override
    public TimeCriterion map(Object source) {
        TimeCriterion criterion = new TimeCriterion();
        map(source, criterion);

        return criterion;
    }

    @Override
    public void map(Object source, TimeCriterion target) {
        target.setValue((String) source);
    }
}
