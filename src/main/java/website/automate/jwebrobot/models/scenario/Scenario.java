package website.automate.jwebrobot.models.scenario;

import website.automate.jwebrobot.models.scenario.actions.Action;

import java.util.List;

public class Scenario {
    private String name;
    private List<Action> steps;
    private int precedence = -1;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrecedence() {
        return precedence;
    }

    public void setPrecedence(int precedence) {
        this.precedence = precedence;
    }

    public List<Action> getSteps() {
        return steps;
    }

    public void setSteps(List<Action> steps) {
        this.steps = steps;
    }
}
