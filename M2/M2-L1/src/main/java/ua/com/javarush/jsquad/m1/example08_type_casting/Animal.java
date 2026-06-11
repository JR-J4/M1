package ua.com.javarush.jsquad.m1.example08_type_casting;

/**
 * Базовий клас Animal (Тварина) — "Предок".
 */
public class Animal {

    protected String name;

    public Animal(String name) {
        this.name = name;
    }

    public void eat() {
        System.out.println("  " + name + " їсть.");
    }
}
