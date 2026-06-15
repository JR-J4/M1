package ua.com.javarush.jsquad.m1.example10_low_coupling_summary;

/**
 * Інтернет-магазин — "верхній шар" програми.
 *
 * <p>Ключова деталь: магазин залежить ЛИШЕ від інтерфейсу {@link PaymentMethod},
 * а не від конкретних класів {@code CardPayment}, {@code PayPalPayment} тощо.
 * Це <b>слабка зв'язність</b>: внутрішню структуру оплати можна змінювати або
 * додавати нові способи — і магазин не доведеться переробляти.</p>
 */
public class OnlineStore {

    // checkout приймає БУДЬ-ЯКИЙ спосіб оплати, що реалізує інтерфейс.
    // Поліморфізм: який саме pay() виконається — вирішує переданий об'єкт.
    public void checkout(String product, double price, PaymentMethod payment) {
        System.out.println("Замовлення: \"" + product + "\" (" + price + ")");
        System.out.println("Спосіб оплати: " + payment.name());
        boolean ok = payment.pay(price);
        System.out.println(ok ? "  ✔ Оплачено. Дякуємо за покупку!\n" : "  ✖ Оплата не пройшла.\n");
    }

    // checkout приймає БУДЬ-ЯКИЙ спосіб оплати, що реалізує інтерфейс.
    // Поліморфізм: який саме pay() виконається — вирішує переданий об'єкт.
    public void checkout(String product, double price, PayPalPayment payment) {
        System.out.println("Замовлення: \"" + product + "\" (" + price + ")");
        System.out.println("Спосіб оплати: " + payment.name());
        boolean ok = payment.pay(price);
        System.out.println(ok ? "  ✔ Оплачено. Дякуємо за покупку!\n" : "  ✖ Оплата не пройшла.\n");
    }

    // checkout приймає БУДЬ-ЯКИЙ спосіб оплати, що реалізує інтерфейс.
    // Поліморфізм: який саме pay() виконається — вирішує переданий об'єкт.
    public void checkout(String product, double price, CardPayment payment) {
        System.out.println("Замовлення: \"" + product + "\" (" + price + ")");
        System.out.println("Спосіб оплати: " + payment.name());
        boolean ok = payment.pay(price);
        System.out.println(ok ? "  ✔ Оплачено. Дякуємо за покупку!\n" : "  ✖ Оплата не пройшла.\n");
    }
}
