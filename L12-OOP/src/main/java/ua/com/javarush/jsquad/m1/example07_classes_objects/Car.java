package ua.com.javarush.jsquad.m1.example07_classes_objects;

/**
 * Клас Car — шаблон (креслення) для створення об'єктів автомобілів.
 *
 * Клас визначає, які дані (поля) та які дії (методи) має об'єкт.
 * Поля — характеристики автомобіля.
 * Методи — дії, які автомобіль може виконувати.
 */
public class Car {

    static final String type = "Vehicle";

    // Поля класу — характеристики автомобіля
    private String brand;           // марка
    private String model;           // модель
    private int year;               // рік випуску

    // State
    private double fuelLevel;       // рівень палива (літри)
    private boolean isRunning;      // чи заведений двигун


    public Car() {
        System.out.println("Car was created");
    }

    public Car(String brand, String model, int year) {
        this.brand = brand;
        this.model = model;
        this.year = year;
    }


    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return brand + model;
    }

    public int getYear() {
        return year;
    }

    public double getFuelLevel() {
        return fuelLevel;
    }

    public boolean isRunning() {
        return isRunning;
    }

    // Метод — дія, яку може виконувати автомобіль
    void start() {
        if (isRunning) {
            System.out.println(brand + " " + model + " вже заведений!");
        } else if (fuelLevel <= 0) {
            System.out.println(brand + " " + model + " не може завестися — немає палива!");
        } else {
            isRunning = true;
            System.out.println(brand + " " + model + " заведений! Vruum!");
        }
    }

    void stop() {
        if (!isRunning) {
            System.out.println(brand + " " + model + " і так не працює.");
        } else {
            isRunning = false;
            System.out.println(brand + " " + model + " заглушений.");
        }
    }

    void drive(double km) {
        if (!isRunning) {
            System.out.println("Спочатку заведіть " + brand + " " + model + "!");
            return;
        }

        double fuelNeeded = km * 0.08; // витрата 8 л/100 км
        if (fuelLevel < fuelNeeded) {
            System.out.println("Недостатньо палива для поїздки " + km + " км!");
            return;
        }

        fuelLevel -= fuelNeeded;
        System.out.println(brand + " " + model + " проїхав " + km + " км. Залишилось палива: " + fuelLevel + " л");
    }

    void showInfo() {
        System.out.println(brand + " " + model + " (" + year + "), паливо: " + fuelLevel + " л, двигун: " + (isRunning ? "працює" : "вимкнений"));
    }

}
