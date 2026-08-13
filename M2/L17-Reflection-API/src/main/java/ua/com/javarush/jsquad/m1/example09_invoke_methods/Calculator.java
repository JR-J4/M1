package ua.com.javarush.jsquad.m1.example09_invoke_methods;

/**
 * Калькулятор із методами різних видів — публічними, приватним, статичним,
 * void та таким, що кидає виняток.
 */
public class Calculator {

    private double memory;

    public int add(int a, int b) {
        return a + b;
    }

    public double divide(double a, double b) {
        if (b == 0) {
            throw new ArithmeticException("Ділення на нуль!");
        }
        return a / b;
    }

    /** void-метод: результату немає, але стан об'єкта змінюється. */
    public void saveToMemory(double value) {
        this.memory = value;
    }

    public double getMemory() {
        return memory;
    }

    /** Статичний метод — для виклику об'єкт не потрібен. */
    public static int square(int value) {
        return value * value;
    }

    /** Приватний метод — рефлексія дістане і його. */
    private String formatResult(double value) {
        return String.format("Результат: %.2f", value);
    }
}
