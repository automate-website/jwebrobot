package website.automate.jwebrobot.models.mapper.criteria;

import website.automate.jwebrobot.models.scenario.actions.criteria.TimeoutCriterion;

public class TimeoutCriterionMapper extends CriterionMapper<TimeoutCriterion> {

    @Override
    public String getCriterionName() {
        return TimeoutCriterion.NAME;
    }

    @Override
    public TimeoutCriterion map(Object source) {
        TimeoutCriterion criterion = new TimeoutCriterion();
        map(source, criterion);

        return criterion;
    }

    @Override
    public void map(Object source, TimeoutCriterion target) {
        target.setValue((String) source);
    }
}
