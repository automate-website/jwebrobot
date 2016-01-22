package website.automate.jwebrobot.config.logger;

import com.google.inject.TypeLiteral;
import com.google.inject.spi.TypeEncounter;
import com.google.inject.spi.TypeListener;
import org.slf4j.Logger;

import java.lang.reflect.Field;

public class Slf4jLogListener implements TypeListener {
    @Override
    public <I> void hear(TypeLiteral<I> typeLiteral, TypeEncounter<I> typeEncounter) {
        Class<?> clazz = typeLiteral.getRawType();
        while (clazz != null) {
            for (Field field : clazz.getDeclaredFields()) {
                if (field.getType() == Logger.class &&
                    field.isAnnotationPresent(InjectLogger.class)) {
                    typeEncounter.register(new Slf4jMembersInjector<I>(field));
                }
            }
            clazz = clazz.getSuperclass();
        }
    }
}
