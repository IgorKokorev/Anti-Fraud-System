import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

final class AccountUtils {

    private AccountUtils() { }

    public static void increaseBalance(Account account, long amount) {
        Field field = null;
        try {
            field = account.getClass().getDeclaredField("balance");
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
        field.setAccessible(true);
        try {
            if (field.getType() == long.class && !Modifier.isFinal(field.getModifiers())) {
                field.set(account, field.getLong(account) + amount);
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}