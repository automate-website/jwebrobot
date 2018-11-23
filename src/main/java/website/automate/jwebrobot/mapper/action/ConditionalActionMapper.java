package website.automate.jwebrobot.mapper.action;

import website.automate.jwebrobot.utils.Mapper;
import website.automate.waml.io.model.main.action.ConditionalAction;

public abstract class ConditionalActionMapper<T extends ConditionalAction> implements Mapper<T, T> {

    @Override
    public T map(T source) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void map(T source, T target) {
        target.setWhen(source.getWhen());
        target.setUnless(source.getUnless());
    }

    abstract public Class<T> getSupportedType();
}
