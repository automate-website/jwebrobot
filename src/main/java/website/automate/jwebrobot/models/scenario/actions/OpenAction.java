package website.automate.jwebrobot.models.scenario.actions;

import website.automate.jwebrobot.models.scenario.actions.criteria.UrlCriterion;

public class OpenAction extends Action {
    public final static String ACTION_NAME = "open";

    private static final String DEFAULT_CRITERION_NAME = UrlCriterion.NAME;

    public UrlCriterion getUrl() {
        return (UrlCriterion) criteriaMap.get(UrlCriterion.NAME);
    }

    public void setUrl(String url) {
        UrlCriterion urlCriterion = new UrlCriterion();
        urlCriterion.setValue(url);
        criteriaMap.put(UrlCriterion.NAME, urlCriterion);
    }

    @Override
    public String getDefaultCriterionName() {
        return DEFAULT_CRITERION_NAME;
    }
}
