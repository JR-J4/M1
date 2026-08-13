package ua.com.javarush.jsquad.m1.example04_upcasting;

/**
 * Автомобіль — спадкоємець {@link Vehicle}. Додає власний метод {@code openTrunk()},
 * якого немає в батьківського класу.
 */
public class Car extends Vehicle {

    public Car(String model) {
        super(model);
    }

    // Метод, який є ЛИШЕ в Car (немає у Vehicle)
    public void openTrunk() {
        System.out.println("  " + model + ": відкрито багажник.");
    }
}
