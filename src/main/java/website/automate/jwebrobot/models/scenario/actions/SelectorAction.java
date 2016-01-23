package website.automate.jwebrobot.models.scenario.actions;


import website.automate.jwebrobot.models.scenario.actions.criteria.Criterion;
import website.automate.jwebrobot.models.scenario.actions.criteria.SelectorCriterion;

public abstract class SelectorAction<T extends Criterion> extends IfUnlessAction {

    public SelectorCriterion getSelector() {
        return (SelectorCriterion) criteriaMap.get(SelectorCriterion.NAME);
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
