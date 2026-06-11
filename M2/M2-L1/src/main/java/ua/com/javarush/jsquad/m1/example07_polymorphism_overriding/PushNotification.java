package ua.com.javarush.jsquad.m1.example07_polymorphism_overriding;

/**
 * Push-сповіщення. НЕ замінює метод повністю, а лише <b>доповнює</b> його:
 * спершу виконує свій код, а потім викликає оригінальний метод батька
 * через {@code super.send()}.
 *
 * <p>Зі слайду лекції: «Якщо потрібно не замінити успадкований метод, а лише
 * трохи доповнити його, можна виконати в новому методі свій код та викликати
 * метод базового класу: {@code super.method()}».</p>
 */
public class PushNotification extends NotificationBase {

    public PushNotification(String message) {
        super(message);
    }

    @Override
    public void send() {
        System.out.println("  🔔 Спершу вмикаю вібрацію та звук...");
    }
}
