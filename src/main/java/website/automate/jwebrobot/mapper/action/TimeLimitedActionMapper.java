package website.automate.jwebrobot.mapper.action;

import website.automate.waml.io.model.action.TimeLimitedAction;

public abstract class TimeLimitedActionMapper<T extends TimeLimitedAction> extends ConditionalActionMapper<T> {

    @Override
    public T map(T source) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void map(T source, T target) {
        super.map(source, target);
        target.setTimeout(source.getTimeout());
    }

}
