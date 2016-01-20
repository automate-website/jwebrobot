package website.automate.executors.jwebrobot.models.scenario;

import website.automate.executors.jwebrobot.models.scenario.actions.Action;

import java.util.List;

public class Scenario {
    private String name;
    private List<Action> steps;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Action> getSteps() {
        return steps;
    }

    public void setSteps(List<Action> steps) {
        this.steps = steps;
    }
}
