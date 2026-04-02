package ua.com.javarush.jsquad.m1.example02_constructors;

/**
 * Клас Smartphone — демонстрація конструкторів.
 *
 * Конструктор — спеціальний метод, який викликається при створенні об'єкта.
 * Ім'я конструктора збігається з ім'ям класу. Він не має типу повернення.
 */
public class Smartphone {

    // Поля класу
    private String brand;
    private String model;      //  == Null
    private int storage;       // пам'ять у ГБ
    private int batteryLevel;  // рівень заряду %

    // Конструктор — ім'я = ім'я класу, без типу повернення
    public Smartphone(String brand, String model, int storage) {
        // this — посилання на поточний об'єкт
        // Потрібен, коли ім'я параметра збігається з ім'ям поля
        this.brand = brand;
        this.model = model;
        this.storage = storage;
        this.batteryLevel = 100; // новий телефон заряджений на 100%

        System.out.println("Конструктор викликано! Створено: " + brand + " " + model);
    }

    public void showInfo() {
        System.out.println(brand + " " + model + " (" + storage + " ГБ), заряд: " + batteryLevel + "%");
    }

    public void use(int hours) {
        int drain = hours * 10; // 10% за годину
        if (drain > batteryLevel) {
            System.out.println(brand + " " + model + " розрядився!");
            batteryLevel = 0;
        } else {
            batteryLevel -= drain;
            System.out.println(brand + " " + model + " використовувався " + hours + " год. Заряд: " + batteryLevel + "%");
        }
    }

    public void charge() {
        batteryLevel = 100;
        System.out.println(brand + " " + model + " заряджено до 100%!");
    }
}
