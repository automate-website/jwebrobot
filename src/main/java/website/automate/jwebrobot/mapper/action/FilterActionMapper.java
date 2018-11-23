package website.automate.jwebrobot.mapper.action;

import website.automate.waml.io.model.main.action.FilterAction;
import website.automate.waml.io.model.main.criteria.FilterCriteria;

public abstract class FilterActionMapper<T extends FilterAction> extends TimeLimitedActionMapper<T> {

    @Override
    public T map(T source) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void map(T source, T target) {
        super.map(source, target);

        FilterCriteria sourceFilter = new FilterCriteria();
        FilterCriteria targetFilter = new FilterCriteria();
        targetFilter.setText(sourceFilter.getText());
        targetFilter.setSelector(sourceFilter.getSelector());
        targetFilter.setValue(sourceFilter.getValue());
        targetFilter.setElement(sourceFilter.getElement());
        targetFilter.setParent(sourceFilter.getParent());
        target.setFilter(targetFilter);
    }

}
