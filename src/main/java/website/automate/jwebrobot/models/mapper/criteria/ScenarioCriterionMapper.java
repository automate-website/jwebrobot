package website.automate.jwebrobot.models.mapper.criteria;

import website.automate.jwebrobot.models.scenario.actions.criteria.ScenarioCriterion;

public class ScenarioCriterionMapper extends CriterionMapper<ScenarioCriterion> {

    @Override
    public String getCriterionName() {
        return ScenarioCriterion.NAME;
    }

    @Override
    public ScenarioCriterion map(Object source) {
        ScenarioCriterion criterion = new ScenarioCriterion();
        map(source, criterion);

        return criterion;
    }

    @Override
    public void map(Object source, ScenarioCriterion target) {
        target.setValue((String) source);
    }
}
