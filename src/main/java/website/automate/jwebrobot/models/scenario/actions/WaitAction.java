package website.automate.jwebrobot.models.scenario.actions;

import website.automate.jwebrobot.models.scenario.actions.criteria.TimeCriterion;

public class WaitAction extends IfUnlessAction<TimeCriterion> {
    public final static String ACTION_NAME = "wait";

    public TimeCriterion getTime() {
        return (TimeCriterion) criteriaMap.get(TimeCriterion.NAME);
    }

    @Override
    public String getDefaultCriterionName() {
        return TimeCriterion.NAME;
    }
}
