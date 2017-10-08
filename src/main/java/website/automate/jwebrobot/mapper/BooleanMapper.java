package website.automate.jwebrobot.mapper;

import website.automate.jwebrobot.exceptions.BooleanExpectedException;

public class BooleanMapper {

    public static boolean isTrue(String rawValue) {
        if ("TRUE".equalsIgnoreCase(rawValue) ||
            "YES".equalsIgnoreCase(rawValue)) {
            return true;
        } else if ("FALSE".equalsIgnoreCase(rawValue) ||
            "NO".equalsIgnoreCase(rawValue)) {
            return false;
        }

        throw new BooleanExpectedException(rawValue);
    }
}
