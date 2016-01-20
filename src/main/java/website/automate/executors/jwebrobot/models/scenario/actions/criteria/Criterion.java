package website.automate.executors.jwebrobot.models.scenario.actions.criteria;


public abstract class Criterion<T> {

    protected T value;

    public abstract String getName();

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    };
}
