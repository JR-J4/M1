package ua.com.javarush.jsquad.m1.example09_dynamic_dispatch;

/**
 * Базовий клас Report (Звіт).
 *
 * <p>Визначає, ЯКІ методи доступні через змінну типу {@code Report}:
 * {@code generate()}. А яка саме реалізація виконається — вирішується
 * під час роботи програми за реальним типом об'єкта.</p>
 */
public class Report {

    protected String title;

    public Report(String title) {
        this.title = title;
    }

    public void generate() {
        System.out.println("  Формую звіт \"" + title + "\" у форматі за замовчуванням.");
    }
}
