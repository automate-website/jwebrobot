package website.automate.jwebrobot.models.scenario.actions;


import website.automate.jwebrobot.models.scenario.actions.criteria.SelectorCriterion;

public class ClickAction extends Action {
    private static final String DEFAULT_CRITERION_NAME = SelectorCriterion.NAME;

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
        return DEFAULT_CRITERION_NAME;
    }


}
