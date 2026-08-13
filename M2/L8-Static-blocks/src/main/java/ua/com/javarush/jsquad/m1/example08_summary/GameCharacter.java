package ua.com.javarush.jsquad.m1.example08_summary;

/**
 * Базовий клас: персонаж гри.
 * Static-частина виконується один раз при завантаженні класу,
 * поля та конструктор — для кожного нового об'єкта.
 */
public class GameCharacter {

    // 1) Static: виконується ОДИН РАЗ, при першому зверненні до класу
    static {
        System.out.println("  [static] GameCharacter: спільні ресурси гри завантажено");
    }

    // 3) Поля базового класу: для КОЖНОГО нового об'єкта
    protected int health = logField("GameCharacter", "health", 100);


    {
        System.out.println("HERO NON static block");
    }

    // 4) Конструктор базового класу
    public GameCharacter() {
        System.out.println("  [ctor]   GameCharacter: персонаж створений, health = " + health);
    }

    protected static int logField(String className, String field, int value) {
        System.out.println("  [поле]   " + className + ": " + field + " = " + value);
        return value;
    }
}
