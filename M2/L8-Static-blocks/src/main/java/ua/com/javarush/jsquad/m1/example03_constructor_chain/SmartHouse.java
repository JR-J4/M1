package ua.com.javarush.jsquad.m1.example03_constructor_chain;

/**
 * Розумний будинок: третій рівень ієрархії.
 * Його конструктор виконується ОСТАННІМ — електроніку ставлять у готовий будинок.
 */
public class SmartHouse extends House {

    public SmartHouse() {
        // Неявний super() -> House() -> а той викличе Building()
        System.out.println("  3. SmartHouse(): розумну електроніку підключено");
    }

    public SmartHouse(int floors) {
        super(floors); // House(int) -> Building(int)
        System.out.println("  3. SmartHouse(" + floors + "): датчики на всіх "
                + floors + " поверхах");
    }
}
