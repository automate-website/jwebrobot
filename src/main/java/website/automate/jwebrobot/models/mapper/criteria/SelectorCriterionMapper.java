package website.automate.jwebrobot.models.mapper.criteria;

import website.automate.jwebrobot.models.scenario.actions.criteria.SelectorCriterion;

public class SelectorCriterionMapper extends CriterionMapper<SelectorCriterion> {

    @Override
    public String getCriterionName() {
        return SelectorCriterion.NAME;
    }

    @Override
    public SelectorCriterion map(Object source) {
        SelectorCriterion criterion = new SelectorCriterion();
        map(source, criterion);

        return criterion;
    }

    @Override
    public void map(Object source, SelectorCriterion target) {
        target.setValue((String) source);
    }
}
