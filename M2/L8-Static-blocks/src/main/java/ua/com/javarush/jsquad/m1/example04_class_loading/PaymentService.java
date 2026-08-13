package ua.com.javarush.jsquad.m1.example04_class_loading;

/**
 * Сервіс оплати.
 * Static змінна та static-блок "голосно" повідомляють про ініціалізацію класу,
 * щоб було видно, КОЛИ саме JVM його завантажила.
 */
public class PaymentService {

    // Ініціалізація класу, крок 1: присвоєння значень static змінним
    static String currency = initCurrency();

    // Ініціалізація класу, крок 2: виконання static-блоків
    static {
        System.out.println("  [PaymentService] static-блок: з'єднання з банком встановлено");
    }

    static void pay(int amount) {
        System.out.println("  Оплата " + amount + " " + currency + " пройшла успішно");
    }

    private static String initCurrency() {
        System.out.println("  [PaymentService] static змінна currency = \"UAH\"");
        return "UAH";
    }
}
