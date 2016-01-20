package website.automate.executors.jwebrobot.models.scenario.actions;

import website.automate.executors.jwebrobot.models.scenario.actions.criteria.UrlCriterion;

public class OpenAction extends Action {
    private static final String DEFAULT_CRITERION_NAME = "url";

    public UrlCriterion getUrl() {
        return (UrlCriterion) criteriaMap.get("url");
    }

    @Override
    public String getDefaultCriterionName() {
        return DEFAULT_CRITERION_NAME;
    }
}
