package website.automate.jwebrobot.models.scenario.actions;

import website.automate.jwebrobot.models.scenario.actions.criteria.SelectorCriterion;
import website.automate.jwebrobot.models.scenario.actions.criteria.UrlCriterion;

public class OpenAction extends IfUnlessAction<SelectorCriterion> {
    public final static String ACTION_NAME = "open";

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
        return UrlCriterion.NAME;
    }
}
