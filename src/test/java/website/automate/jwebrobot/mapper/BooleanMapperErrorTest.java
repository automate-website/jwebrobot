package website.automate.jwebrobot.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import website.automate.jwebrobot.exceptions.BooleanExpectedException;

@RunWith(MockitoJUnitRunner.class)
public class BooleanMapperErrorTest {

    @Test(expected = BooleanExpectedException.class)
    public void shouldThrowExceptionOnUnknownRawValue() {
        BooleanMapper.isTrue("bla");
    }

    @Test(expected = BooleanExpectedException.class)
    public void shouldThrowExceptionOnNullRawValue() {
        BooleanMapper.isTrue(null);
    }
}
