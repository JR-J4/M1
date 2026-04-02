package ua.com.javarush.jsquad.m1.example08_oop_principles;

import java.nio.file.Path;

/**
 * Батьківський клас Vehicle (Транспортний засіб).
 *
 * <h4>Інкапсуляція — доступ до полів:</h4>
 * <pre>
 *  Зовнішній код                    Клас Vehicle
 *  ┌────────────┐        ┌──────────────────────────────────┐
 *  │            │        │  private brand ✖ прямий доступ   │
 *  │ vehicle.   │──get──▶│  private model                   │
 *  │ getBrand() │        │  private maxSpeed                │
 *  │            │◀──────│  private engineOn                │
 *  │ vehicle.   │──set──▶│                                  │
 *  │ setMax     │  ✔     │  setBrand(brand) {               │
 *  │ Speed(220) │перевір-│    this.brand = brand;           │
 *  │            │  ка    │  }                               │
 *  │ vehicle.   │──set──▶│  setMaxSpeed(speed) {            │
 *  │ setMax     │  ✖     │    if (speed &lt; 0) return; ← захист!│
 *  │ Speed(-5)  │відхилено│    this.maxSpeed = speed;        │
 *  └────────────┘        └──────────────────────────────────┘
 * </pre>
 *
 * <ul>
 *   <li><b>АБСТРАКЦІЯ:</b> лише важливі характеристики (brand, model, maxSpeed).
 *       Колір, вага, кількість дверей — відкинуті.</li>
 *   <li><b>ІНКАПСУЛЯЦІЯ:</b> поля {@code private} — доступ тільки через getter/setter.</li>
 *   <li><b>УСПАДКУВАННЯ:</b> SportsCar та Truck наслідують цей клас.</li>
 * </ul>
 */
public class Vehicle {

    // Інкапсуляція: поля приховані від прямого доступу
    private String brand;
    private String model;
    private int maxSpeed;
    private boolean engineOn;

    // Методи для доступу до полів (getter/setter)
    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(int maxSpeed) {
        if (maxSpeed < 0) {
            throw new IllegalArgumentException("Швидкість не може бути від'ємною! Input: " + maxSpeed +" | Expected > 0");
        }
        this.maxSpeed = maxSpeed;
    }

    public boolean isEngineOn() {
        return engineOn;
    }

    // Метод запуску двигуна — може бути перевизначений у нащадках (поліморфізм)
    public void startEngine() {
        engineOn = true;
        System.out.println(brand + " " + model + ": двигун запущено.");
    }

    public void stopEngine() {
        engineOn = false;
        System.out.println(brand + " " + model + ": двигун зупинено.");
    }

    // Загальний метод для всіх транспортних засобів
    public void showInfo() {
        System.out.println("  " + brand + " " + model + " | макс. швидкість: " + maxSpeed + " км/г | двигун: " + (engineOn ? "працює" : "вимкнений"));
    }

}
