package website.automate.jwebrobot.models.scenario.actions.criteria;


public class TimeoutCriterion extends Criterion<String> {
    public static final String NAME = "timeout";

    @Override
    public String getName() {
        return NAME;
    }

}
