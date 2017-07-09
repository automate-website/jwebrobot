package website.automate.jwebrobot.mapper;

import website.automate.jwebrobot.exceptions.BooleanExpectedException;
import website.automate.waml.io.model.action.AlertAction;

public class BooleanMapper {
    public static boolean isTrue(AlertAction action, String rawValue) {
        if ("TRUE".equalsIgnoreCase(rawValue) ||
            "YES".equalsIgnoreCase(rawValue)) {
            return true;
        } else if ("FALSE".equalsIgnoreCase(rawValue) ||
            "NO".equalsIgnoreCase(rawValue)) {
            return false;
        }

        throw new BooleanExpectedException(action.getClass(), rawValue);
    }
}
