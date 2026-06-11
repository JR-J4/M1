package ua.com.javarush.jsquad.m1.example08_type_casting;

/**
 * Собака — "Спадкоємець" {@link Animal}. Має власний метод {@code bark()},
 * якого немає в батька.
 */
public class Dog extends Animal {

    public Dog(String name) {
        super(name);
    }

    // Метод, який є ЛИШЕ у Dog
    public void bark() {
        System.out.println("  " + name + ": Гав-гав! 🐶");
    }
}
