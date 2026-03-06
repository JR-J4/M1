package ua.com.javarush.jsquad.m1;

/**
 * Лекція 4: Порівняння рядків (String)
 *
 * Для порівняння рядків (String) є нюанси:
 *   == та != порівнюють АДРЕСИ в пам'яті (чи це той самий об'єкт?)
 *   .equals() порівнює ЗМІСТ рядків (чи текст однаковий?)
 *
 * Аналогія з життя:
 *   == це як запитати "Це ТОЙ САМИЙ аркуш паперу?" (одна й та сама річ)
 *   .equals() це як запитати "Чи написано ОДНЕ Й ТЕ САМЕ на двох аркушах?"
 *
 * Два аркуші можуть мати однаковий текст, але бути різними аркушами!
 *
 * Нюанс: Java-компілятор оптимізує — якщо два рядки в коді однакові,
 * він зберігає лише один об'єкт (String Pool). Тому == іноді працює,
 * але це НЕ надійно! Завжди використовуйте .equals() для рядків.
 *
 * Реальне застосування: перевірка пароля, пошук тексту, порівняння імен.
 */
public class Example09_StringComparison {

    public static void main(String[] args) {

        // ============================================================
        //   Приклад 1: == порівнює адреси, а не зміст
        // ============================================================
        System.out.println("=== == порівнює адреси ===");

        String text = "Привіт";
        String message = text;
        // text і message вказують на ОДИН об'єкт -> адреси рівні

        System.out.println("text == message: " + (text == message));  // true
        System.out.println("(бо це одна й та сама адреса в пам'яті)");
        System.out.println();

        // ============================================================
        //   Приклад 2: .equals() порівнює зміст
        // ============================================================
        System.out.println("=== .equals() порівнює зміст ===");

        String s1 = "Вітаю";
        String s2 = "ВІТАЮ";
        String s3 = s1.toUpperCase(); // "ВІТАЮ" — новий об'єкт!

        System.out.println("s1 = \"" + s1 + "\"");
        System.out.println("s2 = \"" + s2 + "\"");
        System.out.println("s3 = s1.toUpperCase() = \"" + s3 + "\"");
        System.out.println();

        System.out.println("s1.equals(s2): " + s1.equals(s2));  // false (різний регістр)
        System.out.println("s1.equals(s3): " + s1.equals(s3));  // false (різний регістр)
        System.out.println("s2.equals(s3): " + s2.equals(s3));  // true (однаковий зміст!)
        System.out.println();

        // ============================================================
        //   Приклад 3: Нюанс з String Pool
        // ============================================================
        System.out.println("=== String Pool (оптимізація компілятора) ===");

        // Компілятор бачить два однакових рядки в коді і робить один об'єкт
        String a = "Це дуже важливе повідомлення";
        String b = "Це дуже важливе повідомлення";

        System.out.println("a == b: " + (a == b));             // true (String Pool!)
        System.out.println("a.equals(b): " + a.equals(b));     // true
        System.out.println("(обидва true, бо компілятор оптимізував)");
        System.out.println();

        // Але якщо рядок створений динамічно — це ІНШИЙ об'єкт!
        String c = new String("Це дуже важливе повідомлення");
        System.out.println("a == c: " + (a == c));             // false (різні об'єкти!)
        System.out.println("a.equals(c): " + a.equals(c));     // true (зміст однаковий!)
        System.out.println("(== дає false, бо це різні об'єкти в пам'яті)");
        System.out.println();

        // ============================================================
        //   Приклад 4: Практичне використання — перевірка пароля
        // ============================================================
        System.out.println("=== Перевірка пароля ===");

        // Уявіть: форма входу на сайт
        String correctPassword = "JavaRush2024";
        String userInput = "Java" + "Rush" + "2024"; // рядок створений динамічно

        // НЕПРАВИЛЬНО — може не спрацювати:
        // if (userInput == correctPassword) { ... }

        String test = null;

        // ПРАВИЛЬНО — завжди працює:
//        if (userInput.equals(correctPassword)) {
        if (correctPassword.equals(userInput)) {
            System.out.println("Пароль вірний! Ласкаво просимо!");
        } else {
            System.out.println("Невірний пароль!");
        }

        System.out.println();
        System.out.println("ЗАПАМ'ЯТАЙ: для порівняння рядків ЗАВЖДИ використовуй .equals()!");
    }
}
