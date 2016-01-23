package website.automate.jwebrobot.models.scenario.actions;


import website.automate.jwebrobot.models.scenario.actions.criteria.SelectorCriterion;
import website.automate.jwebrobot.models.scenario.actions.criteria.ValueCriterion;

public class SelectAction extends SelectorAction<SelectorCriterion> {
    public final static String ACTION_NAME = "select";

    public ValueCriterion getValue() {
        return (ValueCriterion) criteriaMap.get(ValueCriterion.NAME);
    }
}
