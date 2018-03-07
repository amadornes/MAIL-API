package mail.movetolib.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

public class AnnotationHelper {

    public static <A extends Annotation> A getAnnotation(Method method, Class<A> annotationType) {
        A annotation = method.getAnnotation(annotationType);
        if (annotation != null) return annotation;

        Queue<Method> methods = new ArrayDeque<>();
        methods.add(method);
        while (!methods.isEmpty()) {
            method = methods.poll();

            annotation = method.getAnnotation(annotationType);
            if (annotation != null) return annotation;

            Class<?> owner = method.getDeclaringClass();

            Class<?> superclass = owner.getSuperclass();
            if (superclass != null) {
                for (Method m : superclass.getMethods()) {
                    if (m.getName().equals(method.getName()) && Arrays.equals(m.getParameterTypes(), method.getParameterTypes())) {
                        methods.add(m);
                        break;
                    }
                }
            }

            for (Class<?> itf : owner.getInterfaces()) {
                for (Method m : itf.getMethods()) {
                    if (m.getName().equals(method.getName()) && Arrays.equals(m.getParameterTypes(), method.getParameterTypes())) {
                        methods.add(m);
                        break;
                    }
                }
            }
        }

        return null;
    }

    public static <A extends Annotation> A getAnnotation(Parameter parameter, Class<A> annotationType) {
        A annotation = parameter.getAnnotation(annotationType);
        if (annotation != null) return annotation;

        int index;
        Method method = (Method) parameter.getDeclaringExecutable();
        Parameter[] srcParameters = method.getParameters();
        for (index = 0; index < srcParameters.length; index++) {
            if (srcParameters[index] == parameter) {
                break;
            }
        }

        Queue<Parameter> parameters = new ArrayDeque<>();
        parameters.add(parameter);
        while (!parameters.isEmpty()) {
            parameter = parameters.poll();

            annotation = parameter.getAnnotation(annotationType);
            if (annotation != null) return annotation;

            method = (Method) parameter.getDeclaringExecutable();
            Class<?> owner = method.getDeclaringClass();

            Class<?> superclass = owner.getSuperclass();
            if (superclass != null) {
                for (Method m : superclass.getMethods()) {
                    if (m.getName().equals(method.getName()) && Arrays.equals(m.getParameterTypes(), method.getParameterTypes())) {
                        parameters.add(m.getParameters()[index]);
                        break;
                    }
                }
            }

            for (Class<?> itf : owner.getInterfaces()) {
                for (Method m : itf.getMethods()) {
                    if (m.getName().equals(method.getName()) && Arrays.equals(m.getParameterTypes(), method.getParameterTypes())) {
                        parameters.add(m.getParameters()[index]);
                        break;
                    }
                }
            }
        }

        return null;
    }
}
