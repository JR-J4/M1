package ua.com.javarush.jsquad.m1.example11_summary;

/**
 * Інтерфейс відправника сповіщень.
 * Наявність інтерфейсу дозволить контейнеру загорнути реалізацію в динамічний проксі.
 */
public interface NotificationSender {

    void send(String to, String message);
}
