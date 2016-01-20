package website.automate.executors.jwebrobot.models.mapper.criteria;


import website.automate.executors.jwebrobot.models.scenario.actions.criteria.Criterion;
import website.automate.executors.jwebrobot.utils.Mapper;

public abstract class CriterionMapper<T extends Criterion> implements Mapper<Object, T> {

    public abstract String getCriterionName();
}
