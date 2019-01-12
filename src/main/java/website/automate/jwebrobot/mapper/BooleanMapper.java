package website.automate.jwebrobot.mapper;

import org.apache.commons.lang3.StringUtils;
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

    public static boolean isTruthy(String rawValue) {
        final String normalizedValue = StringUtils.trimToEmpty(rawValue).toUpperCase();
        return "TRUE".equals(normalizedValue) ||
            "YES".equals(normalizedValue) ||
            "1".equals(normalizedValue);
    }
}
