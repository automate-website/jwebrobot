package website.automate.jwebrobot.models.mapper.criteria;

import website.automate.jwebrobot.models.scenario.actions.criteria.TextCriterion;

public class TextCriterionMapper extends CriterionMapper<TextCriterion> {

    @Override
    public String getCriterionName() {
        return TextCriterion.NAME;
    }

    @Override
    public TextCriterion map(Object source) {
        TextCriterion criterion = new TextCriterion();
        map(source, criterion);

        return criterion;
    }

    @Override
    public void map(Object source, TextCriterion target) {
        target.setValue((String) source);
    }
}
