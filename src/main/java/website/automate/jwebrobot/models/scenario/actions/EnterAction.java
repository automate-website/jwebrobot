package website.automate.jwebrobot.models.scenario.actions;

import website.automate.jwebrobot.models.scenario.actions.criteria.ClearCriterion;
import website.automate.jwebrobot.models.scenario.actions.criteria.ValueCriterion;

public class EnterAction extends SelectorAction<ValueCriterion> {
    public final static String ACTION_NAME = "enter";

    public ValueCriterion getValue() {
        return getCriterion(ValueCriterion.NAME, ValueCriterion.class);
    }

    public ClearCriterion getClear() {
        return getCriterion(ClearCriterion.NAME, ClearCriterion.class);
    }

    @Override
    public String getDefaultCriterionName() {
        return ValueCriterion.NAME;
    }
}
