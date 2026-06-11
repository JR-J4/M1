package ua.com.javarush.jsquad.m1.example08_type_casting;

/**
 * Кіт — ще один "Спадкоємець" {@link Animal} зі своїм методом {@code meow()}.
 */
public class Cat extends Animal {

    public Cat(String name) {
        super(name);
    }

    public void meow() {
        System.out.println("  " + name + ": Мяу! 🐱");
    }
}
