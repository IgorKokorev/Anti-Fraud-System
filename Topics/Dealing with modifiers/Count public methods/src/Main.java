import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

class ReflectionUtils {

    public static int countPublicMethods(Class targetClass) {
        Method[] methods = targetClass.getDeclaredMethods();
        int publicMethods = 0;

        for (Method method: methods) {
            if (Modifier.isPublic(method.getModifiers())) {
                publicMethods++;
            }
        }

        return publicMethods;
    }
}