package ua.com.javarush.jsquad.m1.example08_oop_principles;

/**
 * SportsCar — спортивний автомобіль.
 *
 * УСПАДКУВАННЯ: extends Vehicle — отримує всі поля та методи батьківського класу.
 * ПОЛІМОРФІЗМ: перевизначає метод startEngine() — спортивне авто заводиться кнопкою.
 */
public class SportsCar extends Vehicle {

    // Додаткове поле, якого немає у батьківському класі
    boolean hasTurbo;

    // Поліморфізм: той самий метод startEngine(), але інша реалізація
    @Override
    public void startEngine() {
        System.out.println(getBrand() + " " + getModel() + ": натиснуто кнопку START. Двигун ревe! VROOOOM!");
    }

    public void activateTurbo() {
        if (!isEngineOn() && !hasTurbo) {
            System.out.println("Турбо недоступне!");
            return;
        }
        hasTurbo = true;
        System.out.println(getBrand() + " " + getModel() + ": ТУРБО активовано! Максимальна потужність!");
    }
}
