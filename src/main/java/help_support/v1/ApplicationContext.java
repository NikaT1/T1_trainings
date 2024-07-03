package help_support.v1;

import help_support.v1.configuration.Configuration;
import help_support.v1.configuration.Instance;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class ApplicationContext {

    private final Map<Class<?>, Object> instances = new HashMap<>();

    public ApplicationContext() throws InvocationTargetException, IllegalAccessException {
        Reflections reflections = new Reflections("help_support.v1.configuration");
        Set<Class<?>> types = reflections.getTypesAnnotatedWith(Configuration.class);
        List<?> objs = types.stream()
                .map(type -> {
                    try {
                        return type.getDeclaredConstructor().newInstance();
                    } catch (InstantiationException e) {
                        throw new RuntimeException(e);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    } catch (InvocationTargetException e) {
                        throw new RuntimeException(e);
                    } catch (NoSuchMethodException e) {
                        throw new RuntimeException(e);
                    }
                })
                .toList();
        for (Object config : objs) {
            List<Method> methods = Arrays.stream(config.getClass().getMethods())
                    .filter(m -> m.isAnnotationPresent(Instance.class))
                    .toList();
            for (Method method : methods) {
                instances.put(method.getReturnType(), method.invoke(config));
            }
        }
    }

    public <T> T getInstance(Class<T> type) {
        return (T) Optional.ofNullable(instances.get(type))
                .orElseThrow();
    }
}
