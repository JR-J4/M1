package ua.com.javarush.jsquad.m1.example05_inner_classes;

/**
 * Клас Computer — демонстрація внутрішніх (inner) та вкладених статичних (nested) класів.
 *
 * <p>Статичний вкладений клас (static nested class) — CPU:
 *   може існувати незалежно від зовнішнього класу.</p>
 *
 * <p>Внутрішній клас (inner class) — Fan:
 *   потребує екземпляр зовнішнього класу для створення.</p>
 */
public class Computer {

    private String brand;
    private boolean powerOn;

    public Computer(String brand) {
        this.brand = brand;
        this.powerOn = false;
    }

    public void turnOn() {
        this.powerOn = true;
        System.out.println("  " + brand + " увімкнено!");
    }

    public void turnOff() {
        this.powerOn = false;
        System.out.println("  " + brand + " вимкнено.");
    }

    public String getBrand() {
        return brand;
    }

    // ──────────────────────────────────────────────────
    // Статичний вкладений клас (static nested class)
    // Не потребує екземпляра Computer для створення
    // ──────────────────────────────────────────────────
    public static class CPU {
        private String model;
        private int cores;

        public CPU(String model, int cores) {
            this.model = model;
            this.cores = cores;
        }

        public void showSpecs() {
            System.out.println("  CPU: " + model + ", ядер: " + cores);
            // НЕ може звертатися до brand чи powerOn зовнішнього класу!
            // brand — нестатичне поле, а CPU — статичний клас
        }
    }

    // ──────────────────────────────────────────────────
    // Внутрішній клас (inner class, нестатичний)
    // Потребує екземпляр Computer для створення
    // ──────────────────────────────────────────────────
    public class Fan {
        private int speed; // швидкість обертання (RPM)

        public Fan(int speed) {
            this.speed = speed;
        }

        public void spin() {
            // Внутрішній клас МОЖЕ звертатися до полів зовнішнього класу!
            if (powerOn) {
                System.out.println("  Вентилятор " + brand + ": обертається на " + speed + " RPM");
            } else {
                System.out.println("  Вентилятор " + brand + ": комп'ютер вимкнено, вентилятор не працює");
            }
        }
    }
}
