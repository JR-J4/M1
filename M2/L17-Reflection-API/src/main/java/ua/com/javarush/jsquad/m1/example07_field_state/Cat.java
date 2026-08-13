package ua.com.javarush.jsquad.m1.example07_field_state;

/**
 * Кіт із лекції. Поле {@code name} навмисно зроблено <b>public</b> —
 * саме тому в прикладі спрацьовує {@code getField("name")}.
 *
 * <p>Для приватних полів потрібен {@code getDeclaredField()} +
 * {@code setAccessible(true)} — це тема прикладу 08.</p>
 */
public class Cat {

    /** public-поле — його бачить getField(). */
    public String name;

    /** Скільки котів створено — статичне поле. */
    public static int catCount;

    public Cat(String name) {
        this.name = name;
        catCount++;
    }

    @Override
    public String toString() {
        return "Кіт на ім'я " + name;
    }
}
