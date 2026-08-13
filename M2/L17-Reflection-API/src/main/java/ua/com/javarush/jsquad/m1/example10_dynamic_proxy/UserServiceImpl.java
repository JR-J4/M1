package ua.com.javarush.jsquad.m1.example10_dynamic_proxy;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Справжня реалізація сервісу. Вона нічого не знає про проксі, логування
 * чи вимірювання часу — займається лише своєю справою.
 */
public class UserServiceImpl implements UserService {

    private final Map<Integer, String> users = new LinkedHashMap<>();

    public UserServiceImpl() {
        users.put(1, "Олена Кравчук");
        users.put(2, "Андрій Ткаченко");
        users.put(3, "Марія Шевченко");
    }

    @Override
    public String findNameById(int id) {
        sleepABit();                       // імітуємо звернення до бази даних
        String name = users.get(id);
        if (name == null) {
            throw new IllegalArgumentException("Користувача з id=" + id + " не знайдено");
        }
        return name;
    }

    @Override
    public void deleteUser(int id) {
        sleepABit();
        users.remove(id);
    }

    @Override
    public int countUsers() {
        return users.size();
    }

    /** Імітація повільного запиту до бази. */
    private void sleepABit() {
        try {
            Thread.sleep(30);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
