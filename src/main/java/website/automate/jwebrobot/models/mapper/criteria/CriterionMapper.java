package website.automate.jwebrobot.models.mapper.criteria;


import website.automate.jwebrobot.models.scenario.actions.criteria.Criterion;
import website.automate.jwebrobot.utils.Mapper;

public abstract class CriterionMapper<T extends Criterion> implements Mapper<Object, T> {

    public abstract String getCriterionName();
}
