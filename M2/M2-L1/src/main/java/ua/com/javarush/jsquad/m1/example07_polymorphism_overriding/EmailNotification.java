package ua.com.javarush.jsquad.m1.example07_polymorphism_overriding;

import java.util.Iterator;
import java.util.StringTokenizer;

/**
 * Email-сповіщення. ПОВНІСТЮ замінює метод {@code send()} батька.
 */
public class EmailNotification extends NotificationBase implements Iterable<EmailNotification>  {

    private final String email;

    public EmailNotification(String message, String email) {
        super(message);
        this.email = email;
    }

    @Override
    public void send() {
        System.out.println("  📧 Лист на " + email + ": " + message);
    }

    @Override
    public Iterator<EmailNotification> iterator() {
        return null;
    }
}
