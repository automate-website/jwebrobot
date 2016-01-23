package website.automate.jwebrobot.models.scenario.actions;


import website.automate.jwebrobot.models.scenario.actions.criteria.Criterion;
import website.automate.jwebrobot.models.scenario.actions.criteria.IfCriterion;
import website.automate.jwebrobot.models.scenario.actions.criteria.UnlessCriterion;

public abstract class IfUnlessAction<T extends Criterion> extends Action<T> {

    public IfCriterion getIf() {
        return (IfCriterion) criteriaMap.get(IfCriterion.NAME);
    }

    public void setIf(String expression) {
        IfCriterion ifCriterion = new IfCriterion();
        ifCriterion.setValue(expression);

        criteriaMap.put(IfCriterion.NAME, ifCriterion);
    }

    public UnlessCriterion getUnless() {
        return (UnlessCriterion) criteriaMap.get(UnlessCriterion.NAME);
    }

    public void setUnless(String expression) {
        UnlessCriterion unlessCriterion = new UnlessCriterion();
        unlessCriterion.setValue(expression);

        criteriaMap.put(UnlessCriterion.NAME, unlessCriterion);
    }

}
