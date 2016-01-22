package website.automate.jwebrobot.config.logger;

import com.google.inject.MembersInjector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;


public class Slf4jMembersInjector<I> implements MembersInjector<I> {
    private final Field field;
    private final Logger logger;

    Slf4jMembersInjector(Field field) {
        this.field = field;
        this.logger = LoggerFactory.getLogger(field.getDeclaringClass());
        field.setAccessible(true);
    }

    public void injectMembers(I t) {
        try {
            field.set(t, logger);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
