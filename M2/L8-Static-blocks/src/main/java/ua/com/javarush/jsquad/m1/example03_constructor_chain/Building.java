package ua.com.javarush.jsquad.m1.example03_constructor_chain;

/**
 * Базовий клас: будівля.
 * Його конструктор завжди виконується ПЕРШИМ — фундамент кладуть до стін.
 */
public class Building {

    protected int floors;

    public Building() {
        this.floors = 1;
        System.out.println("  1. Building(): фундамент закладено, поверхів: " + floors);
    }

    public Building(int floors) {
        this.floors = floors;
        System.out.println("  1. Building(" + floors + "): фундамент під "
                + floors + "-поверхову будівлю");
    }
}
