package website.automate.jwebrobot.models.scenario.actions;


import website.automate.jwebrobot.models.scenario.actions.criteria.Criterion;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public abstract class Action<T extends Criterion> {
    Map<String, Criterion> criteriaMap = new HashMap<>();

    public void putCriterion(Criterion criterion) {
        // TODO check allowed criteria for the specific action
        criteriaMap.put(criterion.getName(), criterion);
    }

    public Collection<Criterion> getCriteria() {
        return criteriaMap.values();
    }

    public abstract String getDefaultCriterionName();
}
