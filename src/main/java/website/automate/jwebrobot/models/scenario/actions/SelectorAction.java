package website.automate.jwebrobot.models.scenario.actions;


import website.automate.jwebrobot.models.scenario.actions.criteria.Criterion;
import website.automate.jwebrobot.models.scenario.actions.criteria.SelectorCriterion;

public abstract class SelectorAction<T extends Criterion> extends IfUnlessAction<T> {

    public SelectorCriterion getSelector() {
        return getCriterion(SelectorCriterion.NAME, SelectorCriterion.class);
    }

    public void setSelector(String selector) {
        SelectorCriterion selectorCriterion = new SelectorCriterion();
        selectorCriterion.setValue(selector);
        criteriaMap.put(SelectorCriterion.NAME, selectorCriterion);
    }

    @Override
    public String getDefaultCriterionName() {
        return SelectorCriterion.NAME;
    }
}
