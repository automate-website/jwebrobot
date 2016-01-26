package website.automate.jwebrobot.model;

import java.util.List;
import java.util.Objects;

public class Scenario {

    private String name;
    private List<Action> steps;

    private int precedence = -1;
    private String description;
    private boolean fragment = false;
    private String timeout = "1000";
    private String ifCondition;
    private String unlessCondition;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isFragment() {
        return fragment;
    }

    public void setFragment(boolean fragment) {
        this.fragment = fragment;
    }

    public String getTimeout() {
        return timeout;
    }

    public void setTimeout(String timeout) {
        this.timeout = timeout;
    }

    public String getIf() {
        return ifCondition;
    }

    public void setIf(String ifCondition) {
        this.ifCondition = ifCondition;
    }

    public String getUnless() {
        return unlessCondition;
    }

    public void setUnless(String unlessCondition) {
        this.unlessCondition = unlessCondition;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Scenario other = (Scenario) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }
}
