package website.automate.executors.jwebrobot.models.mapper.criteria;

import website.automate.executors.jwebrobot.models.scenario.actions.criteria.SelectorCriterion;

public class SelectorCriterionMapper extends CriterionMapper<SelectorCriterion> {

    @Override
    public String getCriterionName() {
        return SelectorCriterion.NAME;
    }

    @Override
    public SelectorCriterion map(Object source) {
        SelectorCriterion criterion = new SelectorCriterion();
        criterion.setValue((String) source);

        return criterion;
    }

    @Override
    public void map(Object source, SelectorCriterion target) {

    }
}
