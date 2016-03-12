package website.automate.jwebrobot.utils;

import java.util.Comparator;

import website.automate.waml.io.model.Scenario;

public class PrecedenceComparator implements Comparator<Scenario> {
    
    private static final PrecedenceComparator INSTANCE = new PrecedenceComparator();
    
    public static PrecedenceComparator getInstance() {
        return INSTANCE;
    }
    
    @Override
    public int compare(Scenario o1, Scenario o2) {
        return o2.getPrecedence() - o1.getPrecedence() ;
    }
}
