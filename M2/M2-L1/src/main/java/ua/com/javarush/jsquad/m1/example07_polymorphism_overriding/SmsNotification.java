package ua.com.javarush.jsquad.m1.example07_polymorphism_overriding;

/**
 * SMS-сповіщення. Перевизначає {@code send()} власною реалізацією.
 */
public class SmsNotification extends NotificationBase {

    private final String phone;

    public SmsNotification(String message, String phone) {
        super(message);
        this.phone = phone;
    }

    @Override
    public void send() {
        System.out.println("  📱 SMS на " + phone + ": " + message);
    }
}
