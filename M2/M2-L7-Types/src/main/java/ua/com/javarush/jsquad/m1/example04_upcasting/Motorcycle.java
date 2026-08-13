package ua.com.javarush.jsquad.m1.example04_upcasting;

/**
 * Мотоцикл — спадкоємець {@link Vehicle}. Має власний метод {@code doWheelie()}.
 */
public class Motorcycle extends Vehicle {

    public Motorcycle(String model) {
        super(model);
    }

    // Метод, який є ЛИШЕ в Motorcycle
    public void doWheelie() {
        System.out.println("  " + model + ": їде на задньому колесі!");
    }
}
