package ua.com.javarush.jsquad.m1.example04_field_initialization;

/**
 * Клас Payment — приклад із лекції.
 *
 * Демонструє ініціалізацію поля через інше поле.
 * Поля ініціалізуються в порядку оголошення у класі.
 */
public class Payment {

    // Ці поля ініціалізуються в порядку оголошення:
    int price = 100;                                    // 1-й
    double tax = 0.2;                                   // 2-й
    double totalPrice = Math.ceil(price * (1 + tax));   // 3-й — використовує price і tax

    public void showInfo() {
        System.out.println("  price = " + price);
        System.out.println("  tax = " + tax);
        System.out.println("  totalPrice = Math.ceil(" + price + " * (1 + " + tax + ")) = " + totalPrice);
    }
}
