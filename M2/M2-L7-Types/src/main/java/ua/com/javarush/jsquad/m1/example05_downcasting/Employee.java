package ua.com.javarush.jsquad.m1.example05_downcasting;

/**
 * Базовий клас {@code Employee} (Працівник) — спільний "предок".
 */
public class Employee {

    protected String name;

    public Employee(String name) {
        this.name = name;
    }

    public void work() {
        System.out.println("  " + name + " працює.");
    }
}
