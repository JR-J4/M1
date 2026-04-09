package ua.com.javarush.jsquad.m1.example01_static_variables;

/**
 * Клас User — демонстрація статичних змінних.
 *
 * Статична змінна userCount — спільна для ВСІХ об'єктів класу.
 * Вона зберігається у статичному об'єкті класу, а не в кожному екземплярі.
 */
public class User {

    // Статична змінна — спільна для всіх об'єктів
    // Зберігає загальну кількість створених користувачів
    public static int userCount = 5;

    // Звичайні (нестатичні) змінні — у кожного об'єкта свої
    private int id;
    private String nickname;
    private String email;

    public User(String nickname, String email) {
        this.nickname = nickname;
        this.email = email;
        this.id = userCount++; // збільшуємо лічильник при кожному створенні
    }

    public void showInfo() {
        System.out.println("  Користувач: " + nickname + " (" + email + ")");
    }

    public String getNickname() {
        return nickname;
    }
}
