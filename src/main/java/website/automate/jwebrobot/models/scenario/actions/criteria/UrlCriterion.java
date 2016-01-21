package website.automate.jwebrobot.models.scenario.actions.criteria;


public class UrlCriterion extends Criterion<String> {
    public static final String NAME = "url";

    @Override
    public String getName() {
        return NAME;
    }
}
