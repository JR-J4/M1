package ua.com.javarush.jsquad.m1.example08_summary;

/**
 * Герой: нащадок персонажа.
 * Його static-блок виконується ПІСЛЯ static-блоку базового класу,
 * а поля та конструктор — після полів і конструктора базового класу.
 */
public class Hero extends GameCharacter {

    // 2) Static нащадка: один раз, ПІСЛЯ static базового класу
    static {
        System.out.println("  [static] Hero: текстури та звуки героя завантажено");
    }

    // 5) Поля нащадка: після того, як базова частина об'єкта повністю готова
    private int mana = logField("Hero", "mana", 50);

    private final String name;


    {


    }


    // 6) Конструктор нащадка виконується ОСТАННІМ
    public Hero(String name) {
        // тут неявно викликається super() -> GameCharacter()
        mana = 7;
        System.out.println("HERO NON static block");
        System.out.println("HERO NON static block");
        this.name = name;
        System.out.println("  [ctor]   Hero: герой " + name + " готовий до бою "
                + "(health = " + health + ", mana = " + mana + ")");
    }
}
