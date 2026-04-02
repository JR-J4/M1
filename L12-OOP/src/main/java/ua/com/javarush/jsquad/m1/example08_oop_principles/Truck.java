package ua.com.javarush.jsquad.m1.example08_oop_principles;

/**
 * Truck — вантажний автомобіль.
 *
 * УСПАДКУВАННЯ: extends Vehicle — отримує всі поля та методи від Vehicle.
 * ПОЛІМОРФІЗМ: перевизначає метод startEngine() — вантажівка заводиться ключем.
 */
public class Truck extends Vehicle {

    // Додаткове поле — вантажопідйомність (тонн)
    double cargoCapacity;

    // Поліморфізм: той самий метод, але інша реалізація
    @Override
    public void startEngine() {
        System.out.println(getBrand() + " " + getModel() + ": повернуто ключ запалювання. Дизель загуркотів...");
    }

    public void loadCargo(double tons) {
        if (tons > cargoCapacity) {
            System.out.println("Перевантаження! Максимум: " + cargoCapacity + " т, а намагаємось завантажити: " + tons + " т");
            return;
        }
        System.out.println(getBrand() + " " + getModel() + ": завантажено " + tons + " т вантажу.");
    }
}
