package website.automate.jwebrobot.models.scenario.actions;

import website.automate.jwebrobot.models.scenario.actions.criteria.ValueCriterion;

public class EnterAction extends IfUnlessAction<ValueCriterion> {
    public final static String ACTION_NAME = "enter";

    public ValueCriterion getValue() {
        return (ValueCriterion) criteriaMap.get(ValueCriterion.NAME);
    }


    @Override
    public String getDefaultCriterionName() {
        return ValueCriterion.NAME;
    }
}
