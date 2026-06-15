package ua.com.javarush.jsquad.m1.example07_polymorphism_overriding;

import java.io.Serializable;

/**
 * Базовий клас Notification (Сповіщення).
 *
 * <p>Має базову реалізацію {@code send()}. Нащадки можуть її
 * <b>перевизначити</b> (повністю замінити) або <b>доповнити</b> через
 * {@code super.send()}.</p>
 */
public abstract class NotificationBase implements Notification, Serializable {

    protected String message;

    public NotificationBase(String message) {
        this.message = message;
    }

    @Override
    public void send() {
        System.out.println("Base send method");
    }
}
