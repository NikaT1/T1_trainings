package help_support.v1.handler;

import help_support.v1.controllers.Controller;
import help_support.v1.controllers.GetMapping;
import help_support.v1.controllers.PostMapping;
import help_support.v1.servicies.SupportService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.reflections.Reflections;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class HandlerMapperImpl implements HandlerMapper{

    private final ConcurrentHashMap<String, MethodControllerPair> postControllers = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, MethodControllerPair> getControllers = new ConcurrentHashMap<>();

    private class MethodControllerPair {
        public MethodControllerPair(Method method, Object controller) {
            this.controller = controller;
            this.method = method;
        }

        Method method;
        Object controller;
    }


    public HandlerMapperImpl(SupportService supportService) {
        Reflections reflections = new Reflections("help_support.v1.controllers");
        Set<Class<?>> types = reflections.getTypesAnnotatedWith(Controller.class);
        List<?> objs = types.stream()
                .map(type -> {
                    try {
                        return type.getDeclaredConstructor(SupportService.class).newInstance(supportService);
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
        for (Object obj : objs) {
            List<Method> postMethods = Arrays.stream(obj.getClass().getMethods())
                    .filter(m -> m.isAnnotationPresent(PostMapping.class))
                    .toList();
            for (Method method : postMethods) {
                postControllers.put(method.getAnnotation(PostMapping.class).path(), new MethodControllerPair(method, obj));
            }
            List<Method> getMethods = Arrays.stream(obj.getClass().getMethods())
                    .filter(m -> m.isAnnotationPresent(GetMapping.class))
                    .toList();
            for (Method method : getMethods) {
                getControllers.put(method.getAnnotation(GetMapping.class).path(), new MethodControllerPair(method, obj));
            }
        }
    }

    public void mapRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String result;
        response.setStatus(200);
        response.setContentType("text/plain");
        try {
            switch (request.getMethod()) {
                case ("GET"):
                    MethodControllerPair getMethodController = getControllers.get(request.getServletPath());
                    result = (String) getMethodController.method.invoke(getMethodController.controller);
                    break;
                case ("POST"):
                    MethodControllerPair postMethodController = postControllers.get(request.getServletPath());
                    result = (String) postMethodController.method.invoke(postMethodController.controller, request);
                    break;
                default:
                    response.setStatus(500);
                    result = "Unsupported operation";
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
        response.getWriter().write(result);
    }
}
