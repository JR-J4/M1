package ua.com.javarush.jsquad.m1.example10_dynamic_proxy;

/**
 * Інтерфейс сервісу користувачів.
 *
 * <p>Динамічний проксі вміє підміняти <b>лише інтерфейси</b> —
 * саме тому будь-який приклад із Proxy починається з інтерфейсу.</p>
 */
public interface UserService {

    String findNameById(int id);

    void deleteUser(int id);

    int countUsers();
}
