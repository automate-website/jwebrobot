package website.automate.jwebrobot.executor;


import website.automate.jwebrobot.models.scenario.Scenario;

import java.util.Comparator;

public class PrecedenceComparator implements Comparator<Scenario> {
    @Override
    public int compare(Scenario o1, Scenario o2) {
        return o2.getPrecedence() - o1.getPrecedence() ;
    }
}
