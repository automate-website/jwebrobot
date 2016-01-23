package website.automate.jwebrobot.models.scenario.actions;


import website.automate.jwebrobot.models.scenario.actions.criteria.ScenarioCriterion;

public class IncludeAction extends IfUnlessAction<ScenarioCriterion> {
    public final static String ACTION_NAME = "include";

    public ScenarioCriterion getScenario() {
        return (ScenarioCriterion) criteriaMap.get(ScenarioCriterion.NAME);
    }

    @Override
    public String getDefaultCriterionName() {
        return ScenarioCriterion.NAME;
    }
}
