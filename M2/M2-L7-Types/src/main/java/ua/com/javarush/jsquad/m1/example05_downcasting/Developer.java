package ua.com.javarush.jsquad.m1.example05_downcasting;

/**
 * Розробник — спадкоємець {@link Employee}. Має власний метод {@code writeCode()}.
 */
public class Developer extends Employee {

    public Developer(String name) {
        super(name);
    }

    public void writeCode() {
        System.out.println("  " + name + " пише код.");
    }

    @Override
    public void work() {
        writeCode();
    }
}
