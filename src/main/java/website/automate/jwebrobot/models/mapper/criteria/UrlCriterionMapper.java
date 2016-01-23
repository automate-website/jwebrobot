package website.automate.jwebrobot.models.mapper.criteria;

import website.automate.jwebrobot.models.scenario.actions.criteria.UrlCriterion;

public class UrlCriterionMapper extends CriterionMapper<UrlCriterion> {

    @Override
    public String getCriterionName() {
        return UrlCriterion.NAME;
    }

    @Override
    public UrlCriterion map(Object source) {
        UrlCriterion criterion = new UrlCriterion();
        map(source, criterion);

        return criterion;
    }

    @Override
    public void map(Object source, UrlCriterion target) {
        target.setValue((String) source);
    }
}
