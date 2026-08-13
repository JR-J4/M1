package ua.com.javarush.jsquad.m1.example04_upcasting;

/**
 * Базовий клас {@code Vehicle} (Транспорт) — спільний "предок".
 * Уміє лише те, що вміє будь-який транспорт — рухатися.
 */
public abstract class Vehicle {

    protected String model;

    public Vehicle(String model) {
        this.model = model;
    }

    public void move() {
        System.out.println("  " + model + " рухається.");
    }
}
