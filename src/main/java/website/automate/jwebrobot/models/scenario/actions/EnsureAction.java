package website.automate.jwebrobot.models.scenario.actions;

import website.automate.jwebrobot.models.scenario.actions.criteria.SelectorCriterion;
import website.automate.jwebrobot.models.scenario.actions.criteria.TextCriterion;
import website.automate.jwebrobot.models.scenario.actions.criteria.TimeoutCriterion;
import website.automate.jwebrobot.models.scenario.actions.criteria.ValueCriterion;

public class EnsureAction extends SelectorAction<SelectorCriterion> {
    public final static String ACTION_NAME = "ensure";


    public TextCriterion getText() {
        return (TextCriterion) criteriaMap.get(TextCriterion.NAME);
    }

    public TimeoutCriterion getTimeout() {
        return (TimeoutCriterion) criteriaMap.get(TimeoutCriterion.NAME);
    }

    public ValueCriterion getValue() {
        return (ValueCriterion) criteriaMap.get(ValueCriterion.NAME);
    }


}
