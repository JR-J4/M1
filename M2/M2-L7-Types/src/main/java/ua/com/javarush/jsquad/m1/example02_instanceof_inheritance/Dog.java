package ua.com.javarush.jsquad.m1.example02_instanceof_inheritance;

/**
 * Собака — спадкоємець {@link Animal}. Плавати "за контрактом" не вміє
 * (не реалізує {@link Swimmer}).
 */
public class Dog extends Animal {

    public Dog(String name) {
        super(name);
    }

    @Override
    public void move() {
        walk();
    }

    @Override
    public void accept(AnimalVisitor visitor) {
        visitor.visit(this);
    }

    public void walk() {
        System.out.println("Walk");
    }

    public void bark() {
        System.out.println("  " + name + ": Гав!");
    }
}
