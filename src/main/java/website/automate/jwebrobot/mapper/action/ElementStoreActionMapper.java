package website.automate.jwebrobot.mapper.action;

import website.automate.waml.io.model.action.ElementStoreAction;

public abstract class ElementStoreActionMapper<T extends ElementStoreAction> extends FilterActionMapper<T> {

    @Override
    public T map(T source) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void map(T source, T target) {
        super.map(source, target);
        
        target.setStore(source.getStore());
    }

}
