package website.automate.jwebrobot.models.scenario.actions;


import website.automate.jwebrobot.models.scenario.actions.criteria.SelectorCriterion;

public class ClickAction extends Action {
    private static final String DEFAULT_CRITERION_NAME = "selector";

    public SelectorCriterion getSelector() {
        return (SelectorCriterion) criteriaMap.get("selector");
    }

    @Override
    public String getDefaultCriterionName() {
        return DEFAULT_CRITERION_NAME;
    }
}
