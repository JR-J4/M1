package ua.com.javarush.jsquad.m1;

/**
 * Лекцiя 10: Робота з рядками
 *
 * String — незмiнний об'єкт, що зберiгає послiдовнiсть символiв Unicode.
 * Найчастiше рядки створюються через лiтерали в подвiйних лапках.
 *
 * Синтаксис:
 * String text = "Привiт, Java!";
 *
 * Аналогiя з життя: рядок — це ярлик на коробцi. Ми не перешиваємо ярлик,
 * а створюємо новий i наклеюємо поверх старого.
 *
 * Реальне застосування: формування листiв для клiєнтiв, зберiгання лоґiнiв,
 * робота з API, що повертають JSON/HTML.
 */
public class Example01_StringBasics {

    public static void main(String[] args) {

        // ============================================================
        //   Приклад 1: Формування повідомлення для клієнта авіалінії
        // ============================================================
        System.out.println("=== Базове створення рядка та екранування ===");

        String passengerName = "Андрій";
        String message = "Привіт, \"" + passengerName + "\"!\n"
                + "Ваш рейс Київ -> Львів підтверджено.";
        System.out.println(message);
        System.out.println("Довжина повідомлення: " + message.length());
        System.out.println();

        // ============================================================
        //   Приклад 2: Перевірка порожнього поля форми
        // ============================================================
        System.out.println("=== Аналiз введення користувача ===");

        String rawPhone = "   ";
        System.out.println("isEmpty(): " + rawPhone.isEmpty());   // false — є пробіли
        System.out.println("isBlank(): " + rawPhone.isBlank());   // true — лише пробіли

        String normalizedPhone = rawPhone.trim();
        if (normalizedPhone.isEmpty()) {
            System.out.println("Користувач не вказав телефон — показуємо підказку.");
        }
        System.out.println();

        // ============================================================
        //   Приклад 3: Аналіз символів промокоду
        // ============================================================
        System.out.println("=== Символи та масив char ===");

        String promoCode = "YOGA-2024";
        System.out.println("Довжина промокоду: " + promoCode.length());

        char firstChar = promoCode.charAt(0);
        char[] symbols = promoCode.toCharArray();

        System.out.println("Перший символ: " + firstChar);
        System.out.print("Символи по черзі: ");
        for (char symbol : symbols) {
            System.out.print(symbol + " ");
        }
        System.out.println();
    }
}
