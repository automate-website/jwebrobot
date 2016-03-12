package website.automate.jwebrobot.mapper.action;

import website.automate.waml.io.model.action.FilterAction;
import website.automate.waml.io.model.action.ParentCriteria;

public abstract class FilterActionMapper<T extends FilterAction> extends TimeLimitedActionMapper<T> {

    @Override
    public T map(T source) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void map(T source, T target) {
        super.map(source, target);
        target.setSelector(source.getSelector());
        target.setText(source.getText());
        target.setValue(source.getValue());
        
        ParentCriteria sourceParent = source.getParent();
        if(sourceParent != null){
            ParentCriteria targetParent = new ParentCriteria();
            target.setParent(targetParent);
            targetParent.setSelector(sourceParent.getSelector());
            targetParent.setText(sourceParent.getText());
            targetParent.setValue(sourceParent.getValue());
        }
    }

}
