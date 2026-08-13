package ua.com.javarush.jsquad.m1.example05_anonymous_class;

/**
 * Абстрактний клас-сповіщувач.
 *
 * <p>Потрібен, щоб показати: анонімний клас — це не тільки про інтерфейси.
 * Анонімно можна успадкуватися і від звичайного, і від абстрактного класу.</p>
 */
public abstract class Notifier {

    private final String channel;

    protected Notifier(String channel) {     // конструктор із параметром
        this.channel = channel;
    }

    /** Метод, який нащадок мусить перевизначити. */
    public abstract void send(String text);

    /** А цей метод нащадок успадковує готовим. */
    public void report() {
        System.out.println("   Канал: " + channel);
    }
}
