package ua.com.javarush.jsquad.m1.example03_static_methods;

/**
 * Утилітний клас MathHelper — демонстрація статичних методів.
 *
 * Статичні методи не прив'язані до об'єкта — їх можна викликати
 * через ім'я класу: MathHelper.max(a, b).
 * Не потрібно створювати об'єкт для виклику.
 */
public class MathHelper {

    // Статичний метод — знаходить максимум з двох чисел
    public static int max(int a, int b) {
        return (a > b) ? a : b;
    }

    // Статичний метод — знаходить мінімум з двох чисел
    public static int min(int a, int b) {
        return (a < b) ? a : b;
    }

    // Статичний метод — обчислює площу прямокутника
    public static double rectangleArea(double width, double height) {
        return width * height;
    }

    // Статичний метод — обчислює площу кола
    public static double circleArea(double radius) {
        return Math.PI * radius * radius;
    }

    // Статичний метод — перевіряє, чи число парне
    public static boolean isEven(int number) {
        return number % 2 == 0;
    }

    // Статичний метод — може викликати ТІЛЬКИ інші статичні методи/змінні
    public static int clamp(int value, int minVal, int maxVal) {
        // Викликаємо інші статичні методи — це дозволено
        return max(minVal, min(value, maxVal));
    }
}
