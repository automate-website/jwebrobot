package website.automate.executors.jwebrobot.models.mapper.criteria;

import website.automate.executors.jwebrobot.models.scenario.actions.criteria.UrlCriterion;

public class UrlCriterionMapper extends CriterionMapper<UrlCriterion> {

    @Override
    public String getCriterionName() {
        return UrlCriterion.NAME;
    }

    @Override
    public UrlCriterion map(Object source) {
        UrlCriterion criterion = new UrlCriterion();
        criterion.setValue((String) source);

        return criterion;
    }

    @Override
    public void map(Object source, UrlCriterion target) {

    }
}
