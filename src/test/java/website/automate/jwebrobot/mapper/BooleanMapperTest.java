package website.automate.jwebrobot.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(Parameterized.class)
public class BooleanMapperTest {

    private final String rawValue;
    private final Boolean expectedResult;

    public BooleanMapperTest(String rawValue, Boolean expectedResult) {
        this.rawValue = rawValue;
        this.expectedResult = expectedResult;
    }

    @Test
    public void shouldMapStringToBoolean() {
        Boolean actualResult = BooleanMapper.isTrue(rawValue);

        assertThat(expectedResult, is(actualResult));
    }


    @SuppressWarnings("rawtypes")
    @Parameterized.Parameters
    public static Collection booleanValues() {
        return Arrays.asList(new Object[][] {
            { "yes", true },
            { "YES", true },
            { "true", true },
            { "TRUE", true },
            { "no", false },
            { "NO", false },
            { "false", false },
            { "FALSE", false },
        });
    }


}
