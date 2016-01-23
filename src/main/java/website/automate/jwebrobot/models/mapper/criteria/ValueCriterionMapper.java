package website.automate.jwebrobot.models.mapper.criteria;

import website.automate.jwebrobot.models.scenario.actions.criteria.ValueCriterion;

public class ValueCriterionMapper extends CriterionMapper<ValueCriterion> {

    @Override
    public String getCriterionName() {
        return ValueCriterion.NAME;
    }

    @Override
    public ValueCriterion map(Object source) {
        ValueCriterion criterion = new ValueCriterion();
        map(source, criterion);

        return criterion;
    }

    @Override
    public void map(Object source, ValueCriterion target) {
        target.setValue((String) source);
    }
}
